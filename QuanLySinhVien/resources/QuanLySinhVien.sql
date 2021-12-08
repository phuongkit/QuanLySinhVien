DROP DATABASE QuanLySinhVien;

CREATE DATABASE QuanLySinhVien;

USE QUANLYSINHVIEN;

CREATE TABLE ACCOUNT(
	AID NVARCHAR(10) PRIMARY KEY,
	USERNAME NVARCHAR(10) NOT NULL,
	PASSWORD  NVARCHAR(10) NOT NULL,
	PERMISSION INT DEFAULT 1, -- 0: Admin 1: Sinh Vien 2: Giang Vien
	DATEOFCREATE DATE
);

CREATE TABLE FACULTY(
	FID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL
);

CREATE TABLE ROOM(
	RID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL,
    AREA NVARCHAR(10) NOT NULL
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

CREATE TABLE TEACHER(
	TID NVARCHAR(10) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL,
	EMAIL NVARCHAR(50) NOT NULL,
	PHONE NVARCHAR(50) NOT NULL,
	ADDRESS NVARCHAR(100) NOT NULL,
	AID NVARCHAR(10) NOT NULL,
	FOREIGN KEY (AID) REFERENCES ACCOUNT(AID)
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

CREATE TABLE COURSE(
	CID NVARCHAR(15) PRIMARY KEY,
	NAME NVARCHAR(50) NOT NULL,
	DESCRIPTION NVARCHAR(100) NOT NULL,
    NUMBEROFCREDITS INT NOT NULL,
    CHECK (NUMBEROFCREDITS < 4 AND NUMBEROFCREDITS > 0)
);

CREATE TABLE COURSE_CLASS(
	CID NVARCHAR(15) NOT NULL,
	CCID NVARCHAR(15) PRIMARY KEY,
    RID NVARCHAR(10) NOT NULL,
    TID NVARCHAR(10) NOT NULL,
	STATUS BIT DEFAULT 1, -- 1. Mo 0. Dong
	SEMESTER INT NOT NULL,
	DESCRIPTION NVARCHAR(100) NOT NULL,
	FOREIGN KEY (CID) REFERENCES COURSE(CID),
    FOREIGN KEY (RID) REFERENCES ROOM(RID),
    FOREIGN KEY (TID) REFERENCES TEACHER(TID)
);

CREATE TABLE TRANSCRIPT(
	CCID NVARCHAR(15) NOT NULL,
	SID NVARCHAR(10) NOT NULL,
	SCORE FLOAT,
	PRIMARY KEY (CCID, SID),
	FOREIGN KEY (CCID) REFERENCES COURSE_CLASS(CCID),
	FOREIGN KEY (SID) REFERENCES STUDENT(SID)
);

INSERT INTO ACCOUNT VALUES(N'01', N'admin', N'12345', 0, NOW());
INSERT INTO ACCOUNT VALUES(N'02', N'student', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'03', N'teacher', N'12345', 2, NOW());
INSERT INTO ACCOUNT VALUES(N'04', N'teacher1', N'12345', 2, NOW());
INSERT INTO ACCOUNT VALUES(N'05', N'teacher2', N'12345', 2, NOW());
INSERT INTO ACCOUNT VALUES(N'06', N'teacher3', N'12345', 2, NOW());
INSERT INTO ACCOUNT VALUES(N'07', N'teacher4', N'12345', 2, NOW());
INSERT INTO ACCOUNT VALUES(N'08', N'teacher5', N'12345', 2, NOW());
INSERT INTO ACCOUNT VALUES(N'09', N'teacher6', N'12345', 2, NOW());
INSERT INTO ACCOUNT VALUES(N'10',N'student1', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'11',N'student2', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'12',N'student3', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'13',N'student4', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'14',N'student5', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'15',N'student6', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'16',N'student7', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'17',N'student8', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'18',N'student9', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'19',N'student10', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'20',N'student11', N'12345', 1, NOW());
INSERT INTO ACCOUNT VALUES(N'21', N'admin1', N'12345', 0, NOW());
INSERT INTO ACCOUNT VALUES(N'22', N'admin2', N'12345', 0, NOW());
INSERT INTO ACCOUNT VALUES(N'23', N'admin3', N'12345', 0, NOW());
INSERT INTO ACCOUNT VALUES(N'24', N'admin4', N'12345', 0, NOW());

INSERT INTO TEACHER VALUES(N'01', N'Ngô Hiếu Thảo', N'thao1222@gmail.com', N'085395330', 'HCM', '03');
INSERT INTO TEACHER VALUES(N'02', N'Ngô Trà My', N'tramy1999@gmail.com', N'093243430', 'HCM', '04');
INSERT INTO TEACHER VALUES(N'03', N'Định Thi Thiên', N'thien1901@gmail.com', N'02434330', 'HCM', '05');
INSERT INTO TEACHER VALUES(N'04', N'Huỳnh Chí Hữu', N'laohy12@gmail.com', N'04234330', 'HCM', '06');
INSERT INTO TEACHER VALUES(N'05', N'Lê Gia Lộc', N'locxza@gmail.com', N'053595330', 'HCM', '07');
INSERT INTO TEACHER VALUES(N'06', N'Nguyễn Trung', N'trungtrung12@gmail.com', N'054395330', 'HCM', '08');
INSERT INTO TEACHER VALUES(N'07', N'Nguyễn Trung  Khánh Ka', N'trungka@gmail.com', N'08594533', 'HCM', '09');

INSERT INTO FACULTY VALUES(N'01', N'Công nghệ thông tin');
INSERT INTO FACULTY VALUES(N'02', N'Điện-Điện tử');
INSERT INTO FACULTY VALUES(N'03', N'Công nghệ in');
INSERT INTO FACULTY VALUES(N'04', N'Công nghệ may và thời trang');
INSERT INTO FACULTY VALUES(N'05', N'Cơ khí chế tạo máy');

INSERT INTO ROOM VALUES(N'01', N'101', N'A');
INSERT INTO ROOM VALUES(N'02', N'102', N'A');
INSERT INTO ROOM VALUES(N'03', N'103', N'A');
INSERT INTO ROOM VALUES(N'04', N'201', N'A');
INSERT INTO ROOM VALUES(N'05', N'202', N'A');
INSERT INTO ROOM VALUES(N'06', N'101', N'B');
INSERT INTO ROOM VALUES(N'07', N'102', N'B');
INSERT INTO ROOM VALUES(N'08', N'103', N'B');
INSERT INTO ROOM VALUES(N'09', N'201', N'B');
INSERT INTO ROOM VALUES(N'10', N'202', N'B');
INSERT INTO ROOM VALUES(N'11', N'101', N'C');
INSERT INTO ROOM VALUES(N'12', N'102', N'C');
INSERT INTO ROOM VALUES(N'13', N'103', N'C');
INSERT INTO ROOM VALUES(N'14', N'104', N'C');
INSERT INTO ROOM VALUES(N'15', N'105', N'C');


INSERT INTO STUDENT VALUES(N'01', N'Ngô Hiếu Thảo', 0, NOW(), N'thao1222@gmail.com', N'085395330', 'HCM', '02', '01');
INSERT INTO STUDENT VALUES(N'02', N'Ngô Chí Hiếu', 1, NOW(), N'hieupc@gmail.com', N'08654640', 'HCM', '10', '01');
INSERT INTO STUDENT VALUES(N'03', N'Ngô Lê Hà', 0, NOW(), N'ha123@gmail.com', N'05646450', 'HCM', '11', '02');
INSERT INTO STUDENT VALUES(N'04', N'Nguyễn Trung', 1, NOW(), N'trung1991@gmail.com', N'06546460', 'HCM', '12', '03');
INSERT INTO STUDENT VALUES(N'05', N'Phan Khánh', 1, NOW(), N'khanhhai@gmail.com', N'65464654', 'HCM', '13', '04');
INSERT INTO STUDENT VALUES(N'06', N'Phạm Thương', 1, NOW(), N'giathuong1@gmail.com', N'075474530', 'HCM', '14', '05');
INSERT INTO STUDENT VALUES(N'07', N'Võ Văn Minh', 1, NOW(), N'minhkhe@gmail.com', N'0346346', 'HCM', '15', '02');
INSERT INTO STUDENT VALUES(N'08', N'Lê Hiếu Thế', 1, NOW(), N'thehao@gmail.com', N'03535354', 'HCM', '16', '01');
INSERT INTO STUDENT VALUES(N'09', N'Lê Hải Lưu', 0, NOW(), N'luugia@gmail.com', N'045353', 'HCM', '17', '01');
INSERT INTO STUDENT VALUES(N'10', N'Lê Thị Như', 0, NOW(), N'nhu4232@gmail.com', N'01234134', 'HCM', '18', '04');
INSERT INTO STUDENT VALUES(N'11', N'Hà Nam', 1, NOW(), N'namturw@gmail.com', N'0134123', 'HCM', '19', '01');
INSERT INTO STUDENT VALUES(N'12', N'Ngô Trung', 1, NOW(), N'trung242gmail.com', N'043242143', 'HCM', '20', '01');

INSERT INTO ADMIN VALUES(N'01', N'Huỳnh Trung Lưu', N'trungluu@gmail.com', N'08593840', 'HCM', '01');
INSERT INTO ADMIN VALUES(N'02', N'Ngô Hải Đường', N'hauduong112@gmail.com', N'09349283', 'HCM', '21');
INSERT INTO ADMIN VALUES(N'03', N'Lưu Minh Trí', N'minhtri1934@gmail.com', N'0534543', 'HCM', '22');
INSERT INTO ADMIN VALUES(N'04', N'Huỳnh Thị Ngô Huỳnh', N'ngohuynh123@gmail.com', N'05435450', 'HCM', '23');
INSERT INTO ADMIN VALUES(N'05', N'Lê Thị Huỳnh Như', N'nhuhuynhgiapha@gmail.com', N'0857567', 'HCM', '24');

INSERT INTO COURSE VALUES(N'ADPL331379', N'Ngôn ngữ lập trình tiên tiến', N'Tự Chọn', 3);
INSERT INTO COURSE VALUES(N'CAAL230180', N'Kiến trúc máy tính và hợp ngữ', N'Bắt Buộc', 3);
INSERT INTO COURSE VALUES(N'CLCO332779', N'Điện toán đám mây', N'Tự Chọn', 3);
INSERT INTO COURSE VALUES(N'DIPR430685', N'Xử lý ảnh số', N'Tự Chọn', 3);
INSERT INTO COURSE VALUES(N'DLEA432085', N'Học sâu', N'Tự Chọn', 3);
INSERT INTO COURSE VALUES(N'ECOM430984', N'Thương mại điện tử', N'Tự Chọn', 3);
INSERT INTO COURSE VALUES(N'ENGL230237', N'Anh văn 2', N'Bắt Buộc', 3);
INSERT INTO COURSE VALUES(N'ENGL330337', N'Anh văn 3', N'Bắt Buộc', 3);
INSERT INTO COURSE VALUES(N'ENGL430437', N'Anh văn 4', N'TBắt Buộc', 3);
INSERT INTO COURSE VALUES(N'ESYS431080', N'Hệ thống nhúng', N'Tự Chọn', 3);

INSERT INTO COURSE_CLASS VALUES(N'ADPL331379', N'ADPL331379_01', '01', '01', 1 , 1, N'No');
INSERT INTO COURSE_CLASS VALUES(N'ADPL331379', N'ADPL331379_02', '02', '02', 1 , 1, N'No');
INSERT INTO COURSE_CLASS VALUES(N'ADPL331379', N'ADPL331379_03', '03', '03', 1 , 1, N'No');
INSERT INTO COURSE_CLASS VALUES(N'ADPL331379', N'ADPL331379_04', '04', '04', 1 , 1, N'No');
INSERT INTO COURSE_CLASS VALUES(N'ADPL331379', N'ADPL331379_05', '05', '05', 1 , 1, N'No');

INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'01', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'02', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'03', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'04', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'05', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'06', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'07', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'08', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'09', -1, 1);
INSERT INTO TRANSCRIPT VALUES(N'ADPL331379_01', N'10', -1, 1);