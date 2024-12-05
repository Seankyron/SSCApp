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
      const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET, { expiresIn: '1h' });
      res.json({ token });
    } else {
      res.status(401).json({ message: 'Invalid credentials' });
    }
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};
