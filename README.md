### Geracao da imagem + recursos kubernetes e/ou Openshift
``mvn clean install -DskipTests fabric8:build fabric8:resource``

**Bootstrap da aplicação:**
Como pré-requisito, as seguintes variáveis de ambiente devem estar definidas:
```
export NOTIFICACAO_HOST=https://api-apix.sensedia.com/notification/v1/notifications
export NOTIFICACAO_CLIENT_ID=67558253-50e0-399e-a9ed-5374d8959dfd
export SUBSCRIPTION_NAME=notificacoes-in
export TOPIC_OUT_NAME=notificacoes-out
```
