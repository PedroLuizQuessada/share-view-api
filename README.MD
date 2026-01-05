# Execução local:
1) clonar o repositório https://github.com/PedroLuizQuessada/share-view-stub e executar o comando “mvn install”
2) clonar o repositório https://github.com/PedroLuizQuessada/share-view-model-jpa e executar o comando “mvn install”
3) possuir uma instância do PostgreSQL rodando em sua máquina
4) configurar o arquivo src/main/resources/application-jpa.properties com a url, o username e a password da instância do PostgreSQL
5) configurar o arquivo src/main/resources/application-restclient.properties para apontar para a URL correta de onde o share-view-notification-service estará sendo executado (propriedade notification-service.base-url)
6) Swagger estará disponível em: http://localhost:8080/share-view/swagger-ui/index.html
<br><br>obs.: ao executar, o usuário de e-mail "email1@email.com" e senha "SenhaUsuario1" será inserido no banco de dados

# Alterações para ambientes corporativos:
1) Adição do arquivo src/main/resources/application-jpa.properties no .gitignore
2) Adição dos arquivos da pasta src/main/resources/keys no .gitignore