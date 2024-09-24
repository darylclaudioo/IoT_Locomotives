const mongoose = require("mongoose");
const KafkaConfig = require("./config.js");
const schema = require("./database.js");

const sendMessageToKafka = async (req, res) => {
    try{
        const messages = req.body;
        const kafkaConfig = new KafkaConfig;

        await kafkaConfig.produce("locomotive", messages);

        const receiveData = JSON.parse(messages.value.toString());

        const orderMongo = mongoose.model('locomotive', schema);
                  const order = new orderMongo({
                    Code: receiveData.Code,
                    Name: receiveData.Name,
                    Dimension: receiveData.Dimension,
                    Status: receiveData.Status,
                    DateAndTime: receiveData.DateAndTime
                  });
                  
                  const savedOrder = await order.save();
                  console.log('Data sent to MongoDB:', savedOrder);

        res.status(200).json({
            status: "OK!",
            message: "Message Successfully send!"
        });
    }catch(err){
        console.log(err);
        res.status(500).json({
            status: "Error",
            message: "Failed to send the message"
        });
    };
};

const controllers = {sendMessageToKafka};

module.exports = controllers;


