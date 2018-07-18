var express = require('express'),
    app = express(),
    server = require('http').createServer(app);

/**
 * 指定需使用文件的路径
 */
app.use('/resource', express.static(__dirname + '/resource'));
app.use('/user', express.static(__dirname + '/ftp4node'));

/**
 * 绑定端口
 */
server.listen(process.env.PORT || 3000);
