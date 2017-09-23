1、主要是基于mqtt的消息推送服务，本系统的设计采用springboot+mqtt来实现消息推送
2、采用kafka作为数据获取后暂存点，可以通过接口获取数据（sub），或则通过客户端发送到mqtt订阅的方式获取对应的消息
3、消息传输格式采用json，内部定义了一些常用的消息作为本服务的一个约束
4、需要搭建mqtt服务器，集群或则单机，具体搭建需要参考mqtt
5、这边项目还需要redis作为缓存工具，主要是缓存用户的发送信息

@RestController
@RequestMapping("/api/push-server/")
public class RegisterController {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 进行注册
     */
    @RequestMapping(value = "unRegist",method = RequestMethod.POST)
    public void unRegist(RegistDto registDto){
        redisTemplate.boundSetOps(registDto.getTopicPre()).add(registDto.getMark());
    }

    /**
     * 取消注册
     * @param registDto
     */
    @RequestMapping(value = "regist",method = RequestMethod.POST)
    public void regist(RegistDto registDto){
        redisTemplate.boundSetOps(registDto.getTopicPre()).remove(registDto.getMark());
    }
}
进行对应的订阅渠道注册，主要是消息推送的时候能针对性的推送

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:31
 */
@RestController
@RequestMapping("/api/push-server/")
public class PushController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MqttClient mqttProducer;

    /**
     *
     * @param pushMsgDto
     */
    @RequestMapping(value = "pushTopic",method = RequestMethod.POST)
    public void pushTopic(PushMsgDto pushMsgDto){
        Set<String> datas = redisTemplate.boundSetOps(pushMsgDto.getTopicPre()).members();
        if(datas.isEmpty()){
            throw new PushException("the register user is empty");
        }else{
            datas.forEach(data->{
                MqttMessage message = new MqttMessage();
                message.setQos(pushMsgDto.getQos());
                message.setRetained(false);
                message.setPayload(pushMsgDto.getContent().getBytes());
                try {
                    mqttProducer.publish(pushMsgDto.getTopicPre().concat(data),message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     *
     * @param pushMsgDto
     */
    @RequestMapping(value = "pushOne",method = RequestMethod.POST)
    public void pushOne(PushMsgDto pushMsgDto){
        MqttMessage message = new MqttMessage();
        message.setQos(pushMsgDto.getQos());
        message.setRetained(false);
        message.setPayload(pushMsgDto.getContent().getBytes());
        try {
            mqttProducer.publish(pushMsgDto.getTopicPre().concat(pushMsgDto.getMark()),message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

可以直接调用接口的方式发送推送消息，可以单点推送也可针对对应的渠道推送


/**
 * @Auther yidadi
 * @Date 17-9-4 下午3:42
 */
@RestController
@RequestMapping("/api/push-server/")
public class SubController {
    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     *
     * @param subMsgDto
     */
    @RequestMapping(value = "sub",method = RequestMethod.POST)
    public void sub(SubMsgDto subMsgDto){
        ProducerRecord producerRecord = new ProducerRecord(subMsgDto.getMqtopic(), JsonUtils.toJson(subMsgDto));
        kafkaProducer.send(producerRecord);
    }
} 
可以通过接口方式的获取客户端发送回来的数据




/**
 * @Auther yidadi
 * @Date 17-9-4 下午3:38
 */
public class MqttCallBack implements MqttCallback {
    private KafkaProducer kafkaProducer;

    public MqttCallBack(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        SubMsgDto subMsgDto = (SubMsgDto)JsonUtils.readFromJson(new String(message.getPayload()),SubMsgDto.class);
        kafkaProducer.send(new ProducerRecord(subMsgDto.getMqtopic(),JsonUtils.toJson(subMsgDto)));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}

也可以通过mqtt的方式获取对应的渠道消息发送到kafka，消息体里面需要制定对应的kafka的topic


