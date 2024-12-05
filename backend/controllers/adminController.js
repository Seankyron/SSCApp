const MembershipPayment = require('../models/MembershipPayment');
const LostAndFound = require('../models/LostAndFound');
const Calagapay = require('../models/Calagapay');
const Agapay = require('../models/Agapay');
const ESSCential = require('../models/ESSCential');

// Add membership payment
exports.addMembership = async (req, res) => {
  const { srcode, program, yearLevel, paymentRefNumber } = req.body;

  if (!srcode || !program || !yearLevel || !paymentRefNumber) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  try {
    const payment = new MembershipPayment({ srcode, program, yearLevel, paymentRefNumber });
    await payment.save();
    res.status(201).json({ message: 'Membership added successfully', payment });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Add lost and found item
exports.addLostAndFound = async (req, res) => {
  const { itemDesc, locationFound, whereToClaim } = req.body;

  if (!itemDesc || !locationFound || !whereToClaim) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  try {
    const lostItem = new LostAndFound({ itemDesc, locationFound, whereToClaim });
    await lostItem.save();
    res.status(201).json({ message: 'Lost and found item added successfully', lostItem });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Add Calagapay (calculator lending program)
exports.addCalagapay = async (req, res) => {
  const { srcode, name, program, dateOfBorrowing, dateOfReturn } = req.body;

  if (!srcode || !name || !program || !dateOfBorrowing || !dateOfReturn) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  try {
    const calagapay = new Calagapay({ srcode, name, program, dateOfBorrowing, dateOfReturn });
    await calagapay.save();
    res.status(201).json({ message: 'Calagapay entry added successfully', calagapay });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

exports.addAgapay = async (req, res) => {
  const { srcode, name, program, fileName, paperSize, numberOfCopies, dateOfClaiming, remarks } = req.body;

  if (!srcode || !name || !program || !fileName || !paperSize || !numberOfCopies || !dateOfClaiming) {
    return res.status(400).json({ error: 'All required fields must be filled' });
  }

  try {
    const agapay = new Agapay({
      srcode,
      name,
      program,
      fileName,
      paperSize,
      numberOfCopies,
      dateOfClaiming,
      remarks,
    });
    await agapay.save();
    res.status(201).json({ message: 'Project Agapay entry added successfully', agapay });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Get all Project Agapay entries
exports.getAgapayEntries = async (req, res) => {
  try {
    const entries = await Agapay.find();
    res.status(200).json(entries);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Add ESSCential (sound system program)
exports.addESSCential = async (req, res) => {
  const { srcode, name, program, deviceNeeded, dateOfBorrowing, dateOfReturn } = req.body;

  if (!srcode || !name || !program || !deviceNeeded || !dateOfBorrowing || !dateOfReturn) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  try {
    const essCential = new ESSCential({ srcode, name, program, deviceNeeded, dateOfBorrowing, dateOfReturn });
    await essCential.save();
    res.status(201).json({ message: 'ESSCential entry added successfully', essCential });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};
