const MembershipPayment = require('../models/MembershipPayment');
// Add handlers for each service as needed
exports.addMembership = async (req, res) => {
  const data = req.body;
  try {
    const payment = new MembershipPayment(data);
    await payment.save();
    res.status(201).json({ message: 'Membership added successfully' });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};
