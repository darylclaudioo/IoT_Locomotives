const Kafka = require("kafkajs");

class KafkaConfig{

    constructor(){
        this.kafka = new Kafka({
            clientId : "app-kafka",
            brokers: ['localhost:9092']
        })
        this.producer = this.kafka.producer();
        this.consumer = this.kafka.consumer({groupId:"locomotive-group"})
    }

    async produce(topic, message){
        try{

            await this.producer.connect()

            const messages = [
                {
                  value: JSON.stringify(message), 
                },
            ];

            await this.producer.send({
                topic: topic,
                messages: messages
            })
        }catch(err){
            console.log(err)
        }finally{
            await this.producer.disconnect()
        }
    }

    async consume(topic, callback){
        try{
            await this.consumer.connect()
            await this.consumer.subscribe({topic: topic, fromBeginning: true})
            await this.consumer.run({
                eachMessage: async ({topic, partition, message}) => {
                    const value = JSON.parse(message.value.toString());
                    callback(value);
                }
            })
        }catch(err){
            console.log(err)
        }
    }
}

module.exports = KafkaConfig