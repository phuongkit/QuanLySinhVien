CREATE DATABASE QuanLySinhVien;

USE QUANLYSINHVIEN;

CREATE TABLE ACCOUNT(
	AID NVARCHAR(10) PRIMARY KEY,
	USERNAME NVARCHAR(10) NOT NULL,
	PASSWORD  NVARCHAR(10) NOT NULL,
	PERMISSION INT DEFAULT 1, -- 0: Admin 1: Sinh Vien 2: Giang Vien
	CREATE_DATE DATE
);

CREATE TABLE FACULTY(
	FID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL
);

CREATE TABLE STUDENT(
	SID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL,
	GENDER BIT NOT NULL,
	DATEOFBIRTH DATE NOT NULL,
	EMAIL NVARCHAR(50) NOT NULL,
	PHONE NVARCHAR(50) NOT NULL,
	ADDRESS NVARCHAR(100) NOT NULL,
	AID NVARCHAR(10) NOT NULL,
	FID NVARCHAR(10) NOT NULL,
	FOREIGN KEY (AID) REFERENCES ACCOUNT(AID),
	FOREIGN KEY (FID) REFERENCES FACULTY(FID)
);

CREATE TABLE COURSE(
	CID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL,
	DESCRIPTION NVARCHAR(100) NOT NULL
);

CREATE TABLE COURSE_CLASS(
	CID NVARCHAR(10) NOT NULL,
	CCID NVARCHAR(10) PRIMARY KEY,
	SECTION NVARCHAR(10) NOT NULL,
	SEMESTER NVARCHAR(50) NOT NULL,
	DESCRIPTION NVARCHAR(100) NOT NULL,
	FOREIGN KEY (CID) REFERENCES COURSE(CID)
);

CREATE TABLE TRANSCRIPT(
	CCID NVARCHAR(10) NOT NULL,
	SID NVARCHAR(10) NOT NULL,
	SCORE NVARCHAR(5) NOT NULL,
	SEMESTER NVARCHAR(50) NOT NULL,
	STATUS BIT DEFAULT 1, -- 1. Mo 0. Dong
	PRIMARY KEY (CCID, SID),
	FOREIGN KEY (CCID) REFERENCES COURSE_CLASS(CCID),
	FOREIGN KEY (SID) REFERENCES STUDENT(SID)
);

CREATE TABLE TEACHER(
	TID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL,
	EMAIL NVARCHAR(50) NOT NULL,
	PHONE NVARCHAR(50) NOT NULL,
	ADDRESS NVARCHAR(100) NOT NULL,
	AID NVARCHAR(10) NOT NULL,
	FOREIGN KEY (AID) REFERENCES ACCOUNT(AID)
);

CREATE TABLE TEACHING_CLASS(
	TID NVARCHAR(10) NOT NULL,
	CCID NVARCHAR(10) NOT NULL,
	SEMESTER NVARCHAR(50) NOT NULL,
	FOREIGN KEY (CCID) REFERENCES COURSE_CLASS(CCID),
	FOREIGN KEY (TID) REFERENCES TEACHER(TID)
);

CREATE TABLE ADMIN(
	ID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL,
	EMAIL NVARCHAR(50) NOT NULL,
	PHONE NVARCHAR(50) NOT NULL,
	ADDRESS NVARCHAR(100) NOT NULL,
	AID NVARCHAR(10) NOT NULL,
	FOREIGN KEY (AID) REFERENCES ACCOUNT(AID)
);

INSERT INTO ACCOUNT VALUES(N'01', N'admin', N'12345', 0, NOW());
INSERT INTO ACCOUNT VALUES(N'02', N'student', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'03', N'teacher', N'12345', 2, NOW());

