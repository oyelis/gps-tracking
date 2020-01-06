CREATE TABLE IF NOT EXISTS tbl_user (
    userId INT PRIMARY KEY,
    chatId BIGINT NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    lastActiveDate TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tbl_gpsDevice(
    id INT AUTO_INCREMENT PRIMARY KEY,
    imei VARCHAR(255) NOT NULL,
    isActive BOOLEAN default false,
    userId INT REFERENCES tbl_user(userId)
);

CREATE TABLE IF NOT EXISTS tbl_location(
    id INT AUTO_INCREMENT PRIMARY KEY,
    latitude FLOAT,
    longitude FLOAT,
    date TIMESTAMP,
    gpsDeviceId INT REFERENCES tbl_gpsDevice(id),
    userId INT REFERENCES tbl_user(userId)
);