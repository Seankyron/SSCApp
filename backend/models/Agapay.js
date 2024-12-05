const mongoose = require('mongoose');

const AgapaySchema = new mongoose.Schema({
  srcode: { type: String, required: true }, // Changed to `srcode`
  name: { type: String, required: true },
  program: { type: String, required: true },
  fileName: { type: String, required: true },
  paperSize: { type: String, required: true },
  numberOfCopies: { type: Number, required: true },
  dateOfClaiming: { type: String, required: true },
  remarks: { type: String },
  submittedAt: { type: Date, default: Date.now },
});

module.exports = mongoose.model('Agapay', AgapaySchema);
