const mongoose = require('mongoose');

const PrintingSchema = new mongoose.Schema({
  srCode: { type: String, required: true },
  name: { type: String, required: true },
  program: { type: String, required: true },
  fileName: { type: String, required: true },
});

module.exports = mongoose.model('ProjectAgapay', PrintingSchema);
