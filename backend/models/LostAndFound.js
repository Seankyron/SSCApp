const mongoose = require('mongoose');

const LostAndFoundSchema = new mongoose.Schema({
  itemDescription: { type: String, required: true },
  locationFound: { type: String, required: true },
  whereToClaim: { type: String, required: true },
});

module.exports = mongoose.model('LostAndFound', LostAndFoundSchema);
