const User = require('../models/User');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');

exports.registerUser = async (req, res) => {
  const { name, contactNumber, srCode, departmentName, yearLevel, program, email, password } = req.body;
  try {
    const user = new User({
      name,
      contactNumber,
      srCode,
      departmentName,
      yearLevel,
      program,
      email,
      password
    });
    await user.save();
    res.status(201).json({ message: 'User registered successfully' });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

exports.loginUser = async (req, res) => {
  const { email, password } = req.body;
  try {
    const user = await User.findOne({ email });
    if (user && (await bcrypt.compare(password, user.password))) {
      res.json({
        id: user._id,
        name: user.name,
        contactNumber: user.contactNumber,
        srCode: user.srCode,
        departmentName: user.departmentName,
        yearLevel: user.yearLevel,
        program: user.program,
        email: user.email
      });
    } else {
      res.status(401).json({ message: 'Invalid credentials' });
    }
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

exports.getAllUsers = async (req, res) => {
  try {
    const users = await User.find({});
    res.status(200).json(users);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

