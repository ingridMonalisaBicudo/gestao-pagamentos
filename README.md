# Gestão de Pagamentos API
Projeto criado para realizar a gestão de pagamentos bancários permitindo fazer a criação, atualização e deleção dos registros. 

## Sumário

1. [Instalação e Pré Requisitos](#instalação)
2. [Como executar](#uso)
3. [Como testar](#contribuindo)
4. [Diagrama UML do Banco de Dados pagamentos_db](#contribuindo)

## Instalação e Pré Requisitos

O projeto foi criado utilizando o spring boot framework, com a versão 17 do java , o Maven versão 3.2.4 para o gerenciamento de dependências e o banco de dados PostgreSQL. Então os principais requisitos são:

1. Baixar e instalar o JDK 17
2. Baixar e instalar o Maven versão 3.2.4


## Como executar
O projeto está construido para execução através do docker-compose

``` bash
cd database-docker
docker-compose up -d
```

## Como testar

O projeto contém uma collection com todos os endpoints mapeados para teste: 

https://github.com/ingridMonalisaBicudo/gestao-pagamentos/blob/main/postman/Gestao-Pagamentos.postman_collection.json

## Diagrama 

Conforme decisão de projeto,foi criada uma base de dados chamada pagamentos_db conforme a modelagem abaixo:

https://github.com/ingridMonalisaBicudo/gestao-pagamentos/blob/main/diagrama_uml/Pagamentos_Database_UML.JPG
