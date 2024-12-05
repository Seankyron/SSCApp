const express = require('express');
const dotenv = require('dotenv');
const connectDB = require('./config/db');
const cors = require('cors');
const authRoutes = require('./routes/auth');
const adminRoutes = require('./routes/admin');
const eventRoutes = require('./routes/events');

// Load environment variables from .env file
dotenv.config();

// Connect to MongoDB
connectDB();

const app = express();

// Middleware
app.use(express.json());
app.use(cors());

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/admin', adminRoutes);
//app.use('/api/events', eventRoutes);

// Test Route to check if the server is running
app.get('/', (req, res) => {
  res.send('SSC App API is Running');
});

// Server listening on specified port
const PORT = process.env.PORT || 5000; // Default to port 5000
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
