# cwtsdk-java

1，线上环境域名和IP都无法ping通或连接。。。需要联系客服加入白名单\
2，服务器加白了，不等于你办公室IP加白了，你在办公室本地调试，肯定无法连通。

***

建议你使用 http://api.dianfus.com 沙盒环境调试通（这个没有安全限制，而且跟生产环境几乎一模一样）。。放到服务器后，把gateway\key\secret改掉，就完事

***

注意沙盒环境的账号和正式环境的账号是不一样的，因此 key和secret也不一样。。请勿要使用正式账号的 key用于沙盒环境\
如果您需要在沙盒环境调试，请向工作人员索要沙盒环境的账号

***
在maven引入

        <dependency>
        <groupId>io.github.hinrich-dxstudio</groupId>
        <artifactId>cwtsdk</artifactId>
        <version>1.0</version>
        </dependency>
        
直接使用sdk
