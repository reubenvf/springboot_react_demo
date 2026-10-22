IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'TESTDB')
BEGIN
    CREATE DATABASE TESTDB;
END;
GO

USE TESTDB;
GO

IF OBJECT_ID(N'dbo.customers', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.customers (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(100) NOT NULL,
        email NVARCHAR(150) NOT NULL UNIQUE,
        status NVARCHAR(50) NOT NULL,
        created_at DATETIME2 NOT NULL,
        updated_at DATETIME2 NOT NULL
    );
END;
GO
