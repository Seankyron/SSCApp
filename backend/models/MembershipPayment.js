const mongoose = require('mongoose');

const MembershipSchema = new mongoose.Schema({
  srCode: { type: String, required: true, unique: true },
  program: { type: String, required: true },
  yearLevel: { type: String, required: true },
  paymentRefNumber: { type: String, required: true },
});

module.exports = mongoose.model('MembershipPayment', MembershipSchema);
