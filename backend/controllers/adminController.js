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

exports.addCalagapay = async (req, res) => {
  const {
    srCode,
    name,
    program,
    calculatorNumber, // New field
    purposeOfBorrowing, // New field
    dateOfBorrowing,
    dateOfReturn,
  } = req.body;

  try {
    const calagapay = new Calagapay({
      srCode,
      name,
      program,
      calculatorNumber, // Added to the model
      purposeOfBorrowing, // Added to the model
      dateOfBorrowing,
    });

    await calagapay.save();
    res.status(201).json({
      message: 'Calagapay entry added successfully',
      calagapay,
    });
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
  const { srCode, name, program, deviceNeeded, purpose, date } = req.body;

  try {
    // Create a new ESSCential entry
    const essCential = new ESSCential({
      srCode,
      name,
      program,
      deviceNeeded,
      purpose,
      date: new Date(date),
    });

    // Save the entry to the database
    await essCential.save();
    res.status(201).json({ message: 'ESSCential entry added successfully', essCential });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};


