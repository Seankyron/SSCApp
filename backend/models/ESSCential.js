const mongoose = require('mongoose');

const ESSCentialSchema = new mongoose.Schema({
  srCode: { type: String, required: true },
  name: { type: String, required: true },
  program: { type: String, required: true },
  deviceNeeded: { type: String, required: true },
  dateOfBorrowing: { type: Date, required: true },
  dateOfReturn: { type: Date, required: true },
});

module.exports = mongoose.model('ESSCentialKit', ESSCentialSchema);
