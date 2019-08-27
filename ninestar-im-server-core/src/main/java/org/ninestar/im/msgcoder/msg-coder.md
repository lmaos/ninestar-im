二进制格式化的消息说明

U2 消息版本号
U1 包长度标识符 0000 0000 高位头长度，低位体长度。高低位取值范围 0,1,2,4
U8 消息ID
U(0,1,2,4) 头长度
U(0,1,2,4) 体长度
bytes 头
bytes 体

特殊消息包
消息报最短值 00000000 00000000 00000000 代表心跳包
心跳应答包 00000000 00000000 11111111 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000
心跳应答包将时间戳long的方式返回

BEGIN 包开始

参数 | 作用 | 长度|
 --- | --- | --- |
VERSION | 版本号 | U2 |
SIGNS | 标记 | U1 |
MSGPACKID | 客户端传入包ID | U8 |
HEAD_LENGTH | 头长度 | Ux | 
BODY_LENGTH | 体长度 | Ux |

HEADER信息 JSON

参数 | 作用 | 类型 |
--- | --- | --- |
uri | 资源定位 | STRING |
toUserIds | 发送目标集合 | STRING[] |
fromUserIds | 发送消息的来源 | STRING | 
msgId | 服务器端记录的消息识别ID（服务器生成）| STRING |
timestamp | 服务器收到的消息的时间戳（服务器生成）| LONG |
msgMode | 消息模式private私有，system系统group群组内消息 | STRING | 
groupIds | 组群集合ID，如果msgMod为group时需要传入组群ID | STRING[] |
contentType | 消息类型（内置类型，TEXT，TEXT:*）| STRING |
charsetName | 如果是文本内容时，发送消息指定编码，如果不指定默认为UTF-8 | STRING |
source | 消息来源，IOS、ANDDROD（客户端传入） | STRING |
page | 数据分页，1/3、2/3 | STRING | 

BODY信息

byte[] content 消息内容 

