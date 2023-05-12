------->storage-service中扣减库存开始
------->storage-service中扣减库存结束
onMessage:xid=192.168.0.101:8091:2134522411,branchId=2134522416,branchType=AT,resourceId=jdbc:mysql://127.0.0.1:3306/seata_storage,applicationData=null
Branch committing: 192.168.0.101:8091:2134522411 2134522416 jdbc:mysql://127.0.0.1:3306/seata_storage null
Branch commit result: PhaseTwo_Committed
以上是项目启动后执行分布式事务的日志摘录

AT模式  二阶段提交

注意点 
需要启动nacos seata
nacos版本 1.1.4
seata版本 0.9.0
springboot版本.2.2.2
springcloud版本Hoxton.SR1
springcloud alibaba版本2.1.0
openFeign 2.2.1
------ 版本一定要约定好 否则会出现各种意想不到的问题 耗费大量时间去排查-------

其中nacos直接启动即可  bin目录下  ./startup.sh -m standalone

其中seata安装目录下
seata 修改file.conf database store driver-class-name和 url 用户名和密码
service.vgroup_mapping.fsp_tx_group = "default"
registry.conf可不用改
注意：0.9.0版本不支持mysql 8.0 需要下载8.0jar包导入到安装目录的lib下 否则项目的seata数据库会有问题
项目启动后 也一直报找不到服务
can not get cluster name in registry config 'service.vgroupMapping.fsp_tx_gr

另外vgroup_mapping.fsp_tx_group = "default" 这个要和项目中的file.conf保持一致
否则也会出现问题
一切没有问题后 在seata安装目录 bin下 执行 ./seata-server.sh 即可启动服务


执行sql脚本 包括创建seata数据库 用于记录分布式 全局事务相关信息  以及 业务表中undo_log 回滚日志表


三个项目启动后 可在account服务中的dao执行更新后的下一行代码打断点 （该操作可以让我们看清分布式事务执行的表的数据记录 事务执行后会(一起成功或 一起失败)
将相关表记录进行删除）

此时seata数据库会三张表中会记录 全局事务相关信息 如 全局唯一xid 分支事务id
其他三个业务库中的undo_log表中会记录 before_image 和 after_image
即前置快照 和 后置快照  用于执行二阶段的 全局提交 或 


另外 三个服务之间调用是使用的 openFeign进行远程调用




整个业务模式是
            下单操作 增加订单信息  同时 减库存  同时减少账户余额
            如果在账户业务逻辑中出现超时了 那么此时订单表回滚 库存表回滚
使用seata AT 二阶段提交事务模式  实现分布式环境部署下的全局事务 要么同时提交成功  要么同时提交失败
保证分布式环境下的各业务场景的数据一致性


该项目注意点：需要自定义dataSource 需要用mapper写数据库执行文件  没有尝试使用mybatis-plus去增删改查 可能会出现问题
尝试使用Mybatis-plus
按照https://www.shuzhiduo.com/A/o75N6p0x5W/  说明配置druid数据源 
但order并未实现回滚 可能是版本问题 需要更高的seata版本配合mybatis-plus实现全局事务回滚

目前只能是使用seata0.9.0版本 并自定义配置数据源 扫描mapper包下的xml文件 实现数据回滚

seata分布式事务原理通过该项目可以了解大概 但是高版本seata 还需要去尝试！！

