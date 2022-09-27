# Relatório técnico da Fase 1

## Introdução

Este documento contém os aspectos relevantes do desenho e implementação da fase 1 do projecto de LS.

## Modelação da base de dados

### Modelação conceptual ###

O seguinte diagrama apresenta a modelo entidade-associação para a informação gerida pelo sistema. 

![alt text](https://i.imgur.com/kaqxfkU.png "Modelo entidade-associação")

O modelo conceptual apresenta ainda as seguintes restrições:

* Na tabela Movie o Título do filme e o seu ano de lançamento não podem ser null.

* Na tabela Users o nome do user e o seu email não podem ser null. Existe ainda uma verificação para confirmar que o email
se encontra no formato correto (`% @ %`)

* Na tabela Rating o id do filme não pode ser null de forma a manter a relação entre tabelas.

* Na tabela Review o id do filme, o id do crítico, o sumário, a crítica completa e o rating não podem apresentar valores null. 
O id do filme para manter a relação entre tabelas, o id do crítico para identificar o mesmo e, o sumário, 
a crítica completa e o rating também não podem ser null visto que deste modo a criação da tabela não teria qualquer objetivo.

    
### Modelação física ###

O modelo físico da base de dados está presente em [createSchema.sql](https://github.com/isel-leic-ls/2021-1-LI41N-G1/blob/master/src/test/sql/createSchema.sql).

## Organização do software

### Processamento de comandos

A interface commandHandler tem apenas como objetivo definir o método *execute*.
Qualquer classe que implemente esta interface, nomeadamente todos os comandos criados, tratam de implementar o método. 

A informação necessária para a execução do comando é passada através de uma instância de *CommandRequest* que contém um 
*path* e os *parameters*. Tanto o *path* como os *parameters* são representados pelas suas distintas classes que têm como 
objetivo o processamento e validação dos mesmos.

Esta interface retorna ainda um *CommandResult* que reflete o resultado da execução do comando.

### Encaminhamento dos comandos

A classe *router* é composta por dois métodos: *addRoute()* e *findRoute()*

O primeiro tem como propósito a adição de todos os comandos possíveis durante a inicialização do programa, os quais
serão guardados dentro de uma *Tree* com dois níveis e se encontra estruturada da seguinte forma:

* No primeiro nível encontram-se os métodos: *GET*, *POST* e *EXIT*. De cada um destes nós surgem os vários *pathHandlers* 
possíveis, um para cada comando.
* Os nós de segundo nível são representados pela classe *PathHandlerNode* e dentro dos mesmos
podemos encontrar o *PathTemplate* e o respetivo *CommandHandler*.

A adição dos comandos em si é feita adicionando nós à árvore conforme seja necessário.

A escolha desta estrutura de dados foi feita com base na eficiência de pesquisa devido à divisão inicial em grandes 
categorias (sendo elas até agora os possiveis métodos: *GET*, *POST* e *EXIT*)

O segundo método, *getCommand()* serve para obter o *CommandHandler* de acordo com o *Method* e *Path* inseridos. Este é obtido
realizando uma pesquisa pela àrvore, retornando um *Optional* contendo ou não um *RouteResult* no qual
está presente o respetivo *commandHandler*.

No caso de optional estar vazio é um indicativo de que o comando não foi encontrado.   

No que toca aos parâmetros presentes dentro do *path*, estes são identificados na altura da pesquisa, na chamada do método
*findRoute()*. Nesta pesquisa é comparado o *Path* aos distintos *PathTemplates* presentes na *Tree*, ao ser encontrada 
uma correspondência, qualquer conflito entre diretorias na mesma posição (tendo em que conta que no *PathTemplate* a 
diretoria está envolvida por "{}") é indicativo de que esta é um parâmetro, sendo a diretoria do *PathTemplate*
o nome do parâmetro e a diretoria da *Path* o valor do mesmo.   

### Gestão de ligações

As conexões são criadas através do método *createConnection()*, presente na classe utilitária *ConnectionHandler*, 
na qual se encontram os dados necessários à sua criação. 

Dentro do método *createConnection()* recorremos à classe *PGSimpleDataSource* para criar a conexão à base de dados,
indicando o nome do servidor, o nome da base de dados, o *username* e a *password* para *login*. Por fim é criada a conexão
usando o método de instância *getConnection()*.

Cada comando tem a sua própria conexão, sendo esta criada no início e encerrada após a execução do mesmo terminar.

### Acesso a dados

O acesso aos dados é feito através dos diferentes comandos.
Cada comando tem um conjunto de instruções SQL e parâmetros quando necessários.

No início do comando é criada uma conexão onde são obtidos os parâmetros caso existam e são colocadas as instruções dentro
dos *preparedStatements*, substituindo em seguida os parâmetros com os valores, quando necessário. De seguida é
executada a instrução usando o método de instância *executeQuery*.

No caso dos comandos de obtenção de dados o resultado da execução é guardado num *resultSet*, pelo qual iteramos em
seguida, apresentado os dados relevantes ao utilizador.

Por fim, é feito o *commit* da transação, são fechados o *resultSet*, o *preparedStatement* e a conexão.
Cada comando retorna um *CommandResult*, indicando o sucesso da operação (*SUCCESSFUL_GET* / *SUCCESSFUL_POST*) ou um erro caso se verifique.

Em caso de *SQL Exception* é feito *rollback()* e é devolvido o resultado *SQL_ERROR*.
Existem ainda as possibilidades de haver erros na *path* e nos *parameters*, devolvendo *PATH_ERROR* e *PARAMETERS_ERROR* respetivamente.

### Processamento de erros

O processamento dos erros é feito na classe *App*, dentro do método *processCommandResult()*, o qual recebe um *CommandResult* 
e de acordo com o tipo deste, apresenta uma mensagem ao utilizador indicando o tipo do erro ou o sucesso da operação, 
dando possibilidade ainda ao utilizador de introduzir um novo comando em seguida, caso o comando anterior não tenha sido *EXIT*.

## Avaliação crítica

Na nossa implementação de *Tree* tomámos alguns atalhos devido à proximidade do *deadline*. Uma melhor implementação seria
dividir cada diretoria de *Path* em *nodes* separados. Os *children* desses *nodes* iriam conter então as *sub-diretorias*.
Isto permitiria uma maior *performance* em termos de pesquisa pelo facto de fazer verificações de forma hierárquica.

No que toca a criação de uma conexão para cada comando, uma melhor implementação seria a criação de mais classes utilitárias,
que não só ajudam na criação da conexão mas também na criação dos statements, rollbacks, updates, commits, etc. Esta implementação
permitiria então que em cada método *execute* estaria só a chamada a métodos destas classes utilitárias e instruções que diferenciam
um comando dos outros, evitando assim repetição de grande parte do código.