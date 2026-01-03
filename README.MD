# Execução local:
1) Abrir CMD
2) Acessar o diretório onde consta o jar "h2-2.3.232.jar"
3) Executar o comando "java -cp h2-2.3.232.jar org.h2.tools.Server -tcp -tcpAllowOthers -ifNotExists"
4) Clonar o repositório https://github.com/PedroLuizQuessada/share-view-model-jpa
5) Executar o comando Maven de download de dependências
6) Swagger estará disponível em: http://localhost:8080/share-view/swagger-ui/index.html
<br><br>obs.: ao executar, o usuário de e-mail "email1@email.com" e senha "SenhaUsuario1" será inserido no banco de dados

# Alterações para ambientes corporativos:
1) Adição do arquivo src/main/resources/application-jpa.properties no .gitignore
2) Adição dos arquivos da pasta src/main/resources/keys no .gitignore