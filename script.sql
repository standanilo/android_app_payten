USE [master]
GO
/****** Object:  Database [Store]    Script Date: 7/13/2023 6:32:42 PM ******/
CREATE DATABASE [Store]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Store', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\Store.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Store_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\Store_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Store] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Store].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Store] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Store] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Store] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Store] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Store] SET ARITHABORT OFF 
GO
ALTER DATABASE [Store] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Store] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Store] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Store] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Store] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Store] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Store] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Store] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Store] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Store] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Store] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Store] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Store] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Store] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Store] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Store] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Store] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Store] SET RECOVERY FULL 
GO
ALTER DATABASE [Store] SET  MULTI_USER 
GO
ALTER DATABASE [Store] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Store] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Store] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Store] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Store] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Store] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Store', N'ON'
GO
ALTER DATABASE [Store] SET QUERY_STORE = OFF
GO
USE [Store]
GO
/****** Object:  Table [dbo].[Order_product]    Script Date: 7/13/2023 6:32:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order_product](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[orderID] [int] NOT NULL,
	[productID] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [int] NOT NULL,
 CONSTRAINT [PK_Order_product] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 7/13/2023 6:32:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[orderID] [int] IDENTITY(1,1) NOT NULL,
	[customerName] [varchar](50) NOT NULL,
	[dateOf] [date] NOT NULL,
	[finished] [bit] NOT NULL,
	[price] [int] NOT NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 7/13/2023 6:32:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[productID] [int] IDENTITY(1,1) NOT NULL,
	[productName] [varchar](50) NOT NULL,
	[productPrice] [int] NOT NULL,
	[productImage] [varchar](50) NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Order_product] ON 

INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (131, 80, 6, 2, 700)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (132, 81, 9, 1, 400)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (133, 81, 8, 2, 700)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (134, 82, 8, 1, 350)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (135, 82, 11, 1, 220)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (136, 82, 9, 1, 400)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (137, 83, 6, 1, 350)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (138, 83, 8, 1, 350)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (139, 83, 7, 1, 350)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (140, 84, 7, 2, 700)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (141, 85, 9, 2, 800)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (142, 86, 10, 1, 200)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (143, 86, 8, 2, 700)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (144, 86, 9, 1, 400)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (145, 87, 10, 2, 400)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (146, 87, 9, 1, 400)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (147, 87, 8, 2, 700)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (148, 88, 7, 3, 1050)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (149, 88, 6, 1, 350)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (150, 89, 6, 4, 1400)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (151, 90, 9, 3, 1200)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (152, 91, 8, 3, 1050)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (153, 91, 10, 1, 200)
INSERT [dbo].[Order_product] ([ID], [orderID], [productID], [quantity], [price]) VALUES (154, 91, 11, 1, 220)
SET IDENTITY_INSERT [dbo].[Order_product] OFF
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (80, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 700)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (81, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 1100)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (82, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 970)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (83, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 1050)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (84, N'Danilo', CAST(N'2023-07-13' AS Date), 0, 700)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (85, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 800)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (86, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 1300)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (87, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 1500)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (88, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 1400)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (89, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 1400)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (90, N'Danilo', CAST(N'2023-07-13' AS Date), 0, 1200)
INSERT [dbo].[Orders] ([orderID], [customerName], [dateOf], [finished], [price]) VALUES (91, N'Danilo', CAST(N'2023-07-13' AS Date), 1, 1470)
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
SET IDENTITY_INSERT [dbo].[Product] ON 

INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (1, N'Mala pljeskavica', 250, N'mala_pljeskavica')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (2, N'Velika pljeskavica', 300, N'velika_pljeskavica')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (3, N'Cevapi', 280, N'cevapi')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (4, N'Kobasice', 280, N'kobasice')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (5, N'Gurmanska pljeskavica', 350, N'gurmanska_pljeskavica')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (6, N'Punjena pljeskavica', 350, N'punjena_pljeskavica')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (7, N'Belo meso', 350, N'belo_meso')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (8, N'Pileci batak', 350, N'pileci_batak')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (9, N'Punjeno belo', 400, N'punjeno_belo')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (10, N'Omlet', 200, N'omlet')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (11, N'Omlet sunka', 220, N'omlet_sunka')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (12, N'Omlet pecenica ', 220, N'omlet_pecenica')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (13, N'Omlet suvi vrat', 220, N'omlet_suvi_vrat')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (14, N'Omlet kulen', 220, N'omlet_kulen')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (15, N'Palacinka dzem', 150, N'palacinka_dzem')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (16, N'Palacinka eurokrem', 200, N'palacinka_eurokrem')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (17, N'Palacinka nutela', 220, N'palacinka_nutela')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (18, N'Palacinka sunka', 200, N'palacinka_sunka')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (19, N'Palacinka pecenica', 220, N'palacinka_pecenica')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (20, N'Palacinka suvi vrat', 220, N'palacinka_suvi_vrat')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (21, N'Palacinka kulen', 220, N'palacinka_kulen')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (22, N'Pomfrit', 150, N'pomfrit')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (23, N'Sendvic sunka', 200, N'sendvic_sunka')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (24, N'Sendvic pecenica`', 220, N'sendvic_pecenica')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (25, N'Sendvic suvi vrat', 220, N'sendvic_suvi_vrat')
INSERT [dbo].[Product] ([productID], [productName], [productPrice], [productImage]) VALUES (26, N'Sendvic kulen', 220, N'sendvic_kulen')
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
ALTER TABLE [dbo].[Orders] ADD  CONSTRAINT [DF_Order_finished]  DEFAULT ((0)) FOR [finished]
GO
ALTER TABLE [dbo].[Order_product]  WITH CHECK ADD  CONSTRAINT [FK_Order_product_Product] FOREIGN KEY([productID])
REFERENCES [dbo].[Product] ([productID])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Order_product] CHECK CONSTRAINT [FK_Order_product_Product]
GO
ALTER TABLE [dbo].[Order_product]  WITH CHECK ADD  CONSTRAINT [FK_OrderID] FOREIGN KEY([orderID])
REFERENCES [dbo].[Orders] ([orderID])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Order_product] CHECK CONSTRAINT [FK_OrderID]
GO
USE [master]
GO
ALTER DATABASE [Store] SET  READ_WRITE 
GO
