# rpc-http
an easy rpc use http

### 编译
mvn clean package
<br>
编译完server端和client端运行jar包在各子目录target下

###执行
服务端启动java -jar ./server/target/RpcHttpServer-1.0-SNAPSHOT.jar
<br>
客户端启动java -jar ./client/target/RpcHttpClient-1.0-SNAPSHOT.jar
<br>
测试: <br>
curl http://127.0.0.1:24000/getOne1 <br>
curl http://127.0.0.1:24000/getOne2 <br>
curl http://127.0.0.1:24000/getTwo1 <br>
curl http://127.0.0.1:24000/getTwo2 <br>