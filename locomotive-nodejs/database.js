const mongoose = require('mongoose');
const URL_MONGODB = "mongodb://127.0.0.1:27017/locomotive";

mongoose.connect(URL_MONGODB)
    .then(() => {
        console.log('Successfully Connect to MongoDB');
    })
    .catch((err) => {
        console.error('Failed to connect MongoDB:', err);
    });

const schema = mongoose.Schema({
    kodeLoco : String,
    namaLoco: String,
    dimensiLoco: String,
    status: String,
    tanggal: String,
    jam: String
})

module.exports = schema