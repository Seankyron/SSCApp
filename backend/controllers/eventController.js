const Announcement = require('../models/Announcement');

exports.addAnnouncement = async (req, res) => {
  const { tag, eventHeader, eventDetails, uploader } = req.body;
  try {
    const announcement = new Announcement({ tag, eventHeader, eventDetails, uploader });
    await announcement.save();
    res.status(201).json({ message: 'Announcement added successfully' });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};
