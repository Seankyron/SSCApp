const mongoose = require('mongoose');

const CalagapaySchema = new mongoose.Schema({
  srCode: { type: String, required: true },
  name: { type: String, required: true },
  program: { type: String, required: true },
  dateOfBorrowing: { type: Date, required: true },
  dateOfReturn: { type: Date, required: true },
});

module.exports = mongoose.model('Calagapay', CalagapaySchema);