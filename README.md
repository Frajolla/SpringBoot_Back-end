# SpringBoot_Back-end
<p>Uma aplicação Spring boot com 5 entidades, conforme figura <code>entidades.jpeg</code>, funcionando com autenticação auth0.</p>
<p>Para criar um usuário deve-se fazer o método post de: <code>localhost:8080/users</code>e com JSON no formato de:</p>
<pre style="background-color:#f4f4f4;padding:10px;border-left:5px solid #ccc; color:#333;">
{
    "email":"email",
    "password":"password",
    "role": "USER_ADMINISTRATOR" ou "USER_COSTUMER"
}</pre>
<br>
<p>Login com <code>localhost:8080/users/login</code>:</p>
<pre style="background-color:#f4f4f4;padding:10px;border-left:5px solid #ccc; color:#333;">
{
    "email":"email",
    "password":"password",
}</pre>
<br>
<p>As devidas documentações dos métodos GET, POST, PUT e DELETE estão em [localhost:8080/](http://localhost:8080/swagger-ui/index.html</p>
