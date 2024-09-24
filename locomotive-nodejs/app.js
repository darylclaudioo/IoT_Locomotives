const express = require('express');
const bodyParser = require("body-parser");
const mongoose = require('mongoose');
const { Kafka, Partitioners } = require('kafkajs');
const schema = require('./database');
require('./database');

//Running port test
const app = express();
const port = 3001;
app.listen(port, () => {
    console.log(`Running on port => http://localhost:${port}`);
});

//Kafka
app.use(bodyParser.json());

const kafka = new Kafka({
    clientId: 'my-app',
    brokers: ['localhost:9092']
});

const admin = kafka.admin();

const run = async () => {
    await admin.connect();
    const retentionTime = 2 * 60 * 60 * 60 * 1000;

    try {
        const topic = 'locomotive';
        const topicExists = await admin.listTopics();

        //Create topic name if it does not exist
        if (!topicExists.includes(topic)) {
            await admin.createTopics({
              topics: [
                {
                  topic,
                  numPartitions: 1,
                  replicationFactor: 1,
                  configEntries: [
                    { name: 'retention.ms', value: `${retentionTime}` }
                  ]
                }
              ]
            });
            console.log(`Topic "${topic}" created successfully.`);
        } else {
            console.log(`Topic "${topic}" found.`);
        }

        //Start consumer
        if (topicExists.includes(topic)) {
            const consumer = kafka.consumer({ groupId: 'locomotive-group' });
            await consumer.connect();
            await consumer.subscribe({ topic: 'locomotive' });
      
            await consumer.run({
              eachMessage: async ({ message }) => {
                try {
                  const receiveData = JSON.parse(message.value.toString());
                  console.log('Data received:', receiveData);
      
                  const orderMongo = mongoose.model('locomotive', schema);
                  const order = new orderMongo({
                    kodeLoco: receiveData.kodeLoco,
                    namaLoco: receiveData.namaLoco,
                    dimensiLoco: receiveData.dimensiLoco,
                    status: receiveData.status,
                    tanggal: receiveData.tanggal,
                    jam: receiveData.jam
                  });
                  
                  const savedOrder = await order.save();
                  console.log('Data sent to MongoDB:', savedOrder);
                  return savedOrder;
                } catch (error) {
                  console.error('Error sending data to MongoDB:', error);
                }
              }
            });
        } else {
            console.log('Topic does not exist.');
        }

    } catch (error) {
        console.error('Failed to create topic:', error);
    } finally {
        await admin.disconnect();
    }
};


run().catch(console.error);

app.post("/receive-data", async (req, res) => {
  const producer = kafka.producer({
    createPartitioner: Partitioners.LegacyPartitioner
  });

  await producer.connect();

  const receiveData = req.body;
  console.log("Received data:", receiveData);

  try {
    await producer.send({
      topic: 'locomotive',
      messages: [{ value: JSON.stringify(receiveData) }]
    });

    console.log('Successfully to send the message to kafka:', receiveData);
    res.status(200).json({ message: 'Successfully to send the message to kafka' });
  } catch (error) {
    console.error('Failed to send the message to kafka:', error);
    res.status(500).json({ error: 'Failed to send the message to kafka' });
  } finally {
    await producer.disconnect();
  }
});