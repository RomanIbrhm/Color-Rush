-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Nov 2025 pada 10.36
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `color_rush`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `leaderboard`
--

CREATE TABLE `leaderboard` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `played_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `total_time` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `leaderboard`
--

INSERT INTO `leaderboard` (`id`, `username`, `score`, `played_at`, `total_time`) VALUES
(1, 'roman', 6, '2025-11-24 05:40:24', 8),
(2, 'roman', 9, '2025-11-24 05:40:41', 5),
(3, 'roman', 10, '2025-11-24 05:40:56', 2),
(4, 'roman', 10, '2025-11-24 05:44:29', 1),
(5, 'roman', 9, '2025-11-24 05:45:28', 22),
(6, 'roman', 9, '2025-11-24 05:59:46', 6),
(7, 'roman', 8, '2025-11-24 06:00:02', 9),
(8, 'roman', 8, '2025-11-24 06:00:15', 8),
(9, 'roman', 10, '2025-11-24 06:39:11', 10),
(10, 'roman', 4, '2025-11-24 06:39:26', 10),
(11, 'roman', 10, '2025-11-24 06:39:45', 5),
(12, 'roman', 10, '2025-11-24 06:40:09', 7),
(13, 'roman', 10, '2025-11-24 06:40:34', 4);

-- --------------------------------------------------------

--
-- Struktur dari tabel `level_pool`
--

CREATE TABLE `level_pool` (
  `id` int(11) NOT NULL,
  `level_stage` int(11) NOT NULL,
  `grid_size` int(11) NOT NULL,
  `base_color` varchar(10) NOT NULL,
  `target_color` varchar(10) NOT NULL,
  `time_limit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `level_pool`
--

INSERT INTO `level_pool` (`id`, `level_stage`, `grid_size`, `base_color`, `target_color`, `time_limit`) VALUES
(1, 1, 2, '#FF0000', '#FF6666', 15),
(2, 1, 2, '#00FF00', '#66FF66', 15),
(3, 1, 2, '#0000FF', '#6666FF', 15),
(4, 1, 2, '#FFFF00', '#FFFF99', 15),
(5, 1, 2, '#00FFFF', '#99FFFF', 15),
(6, 1, 2, '#FF00FF', '#FF99FF', 15),
(7, 1, 2, '#FFA500', '#FFCC66', 15),
(8, 1, 2, '#800080', '#B366B3', 15),
(9, 1, 2, '#008000', '#33CC33', 15),
(10, 1, 2, '#000080', '#4D4DFF', 15),
(11, 2, 2, '#B22222', '#E84545', 15),
(12, 2, 2, '#2E8B57', '#4BB876', 15),
(13, 2, 2, '#4682B4', '#70A4D4', 15),
(14, 2, 2, '#D2691E', '#F4984B', 15),
(15, 2, 2, '#9932CC', '#C26BE8', 15),
(16, 2, 2, '#FF1493', '#FF63B1', 15),
(17, 2, 2, '#FF4500', '#FF7A4D', 15),
(18, 2, 2, '#20B2AA', '#5CCCCC', 15),
(19, 2, 2, '#556B2F', '#8A9F5E', 15),
(20, 2, 2, '#8B4513', '#BC7341', 15),
(21, 3, 3, '#1E90FF', '#4DA6FF', 12),
(22, 3, 3, '#32CD32', '#66E066', 12),
(23, 3, 3, '#FFD700', '#FFE44D', 12),
(24, 3, 3, '#8A2BE2', '#A95EF2', 12),
(25, 3, 3, '#FF6347', '#FF8F7D', 12),
(26, 3, 3, '#40E0D0', '#75EBE0', 12),
(27, 3, 3, '#DA70D6', '#E99FE5', 12),
(28, 3, 3, '#CD853F', '#E4AC76', 12),
(29, 3, 3, '#708090', '#92A0AD', 12),
(30, 3, 3, '#6B8E23', '#91B346', 12),
(31, 4, 3, '#DC143C', '#E84A68', 12),
(32, 4, 3, '#00008B', '#0000B8', 12),
(33, 4, 3, '#006400', '#008500', 12),
(34, 4, 3, '#8B008B', '#B300B3', 12),
(35, 4, 3, '#FF8C00', '#FFAB40', 12),
(36, 4, 3, '#9400D3', '#B82EE6', 12),
(37, 4, 3, '#8B0000', '#B81414', 12),
(38, 4, 3, '#2F4F4F', '#456666', 12),
(39, 4, 3, '#483D8B', '#6359A3', 12),
(40, 4, 3, '#00CED1', '#33E0E3', 12),
(41, 5, 4, '#7B68EE', '#9284F2', 10),
(42, 5, 4, '#00FA9A', '#33FCC0', 10),
(43, 5, 4, '#C71585', '#D9429C', 10),
(44, 5, 4, '#191970', '#25258E', 10),
(45, 5, 4, '#696969', '#7D7D7D', 10),
(46, 5, 4, '#A0522D', '#BA704C', 10),
(47, 5, 4, '#556B2F', '#6D8244', 10),
(48, 5, 4, '#808000', '#99991F', 10),
(49, 5, 4, '#48D1CC', '#6CE3DF', 10),
(50, 5, 4, '#C0C0C0', '#D6D6D6', 10),
(51, 6, 4, '#4682B4', '#558DC0', 10),
(52, 6, 4, '#D2691E', '#E07D38', 10),
(53, 6, 4, '#5F9EA0', '#6EAAB0', 10),
(54, 6, 4, '#BDB76B', '#CCC785', 10),
(55, 6, 4, '#FF7F50', '#FF9169', 10),
(56, 6, 4, '#6495ED', '#7AA3F0', 10),
(57, 6, 4, '#DC143C', '#E63255', 10),
(58, 6, 4, '#00FFFF', '#1AFFFF', 10),
(59, 6, 4, '#000080', '#0A0A94', 10),
(60, 6, 4, '#808080', '#8F8F8F', 10),
(61, 7, 4, '#8B4513', '#96501E', 8),
(62, 7, 4, '#2E8B57', '#389662', 8),
(63, 7, 4, '#DAA520', '#E5B030', 8),
(64, 7, 4, '#9932CC', '#A643D6', 8),
(65, 7, 4, '#8FBC8F', '#9CC69C', 8),
(66, 7, 4, '#483D8B', '#534896', 8),
(67, 7, 4, '#2F4F4F', '#395959', 8),
(68, 7, 4, '#00CED1', '#12D9DC', 8),
(69, 7, 4, '#9400D3', '#A015DF', 8),
(70, 7, 4, '#FF4500', '#FF5414', 8),
(71, 8, 5, '#A9A9A9', '#B3B3B3', 7),
(72, 8, 5, '#556B2F', '#5E7339', 7),
(73, 8, 5, '#8B0000', '#960B0B', 7),
(74, 8, 5, '#E9967A', '#F0A087', 7),
(75, 8, 5, '#8FBC8F', '#99C499', 7),
(76, 8, 5, '#483D8B', '#514693', 7),
(77, 8, 5, '#2F4F4F', '#375757', 7),
(78, 8, 5, '#00CED1', '#0DD8DB', 7),
(79, 8, 5, '#9400D3', '#9D0FDC', 7),
(80, 8, 5, '#FF1493', '#FF229D', 7),
(81, 9, 5, '#FFFFFF', '#F5F5F5', 6),
(82, 9, 5, '#F0FFF0', '#E0F0E0', 6),
(83, 9, 5, '#F0FFFF', '#E0FFFF', 6),
(84, 9, 5, '#FFF0F5', '#FFE0E5', 6),
(85, 9, 5, '#FFFFF0', '#FFFFE0', 6),
(86, 9, 5, '#F5F5DC', '#EBEBCC', 6),
(87, 9, 5, '#E6E6FA', '#DCDCF0', 6),
(88, 9, 5, '#FFFACD', '#EEE8AA', 6),
(89, 9, 5, '#E0FFFF', '#D1EEEE', 6),
(90, 9, 5, '#FFE4E1', '#EED5D2', 6),
(91, 10, 6, '#000080', '#000085', 5),
(92, 10, 6, '#800000', '#850000', 5),
(93, 10, 6, '#006400', '#006900', 5),
(94, 10, 6, '#4B0082', '#500087', 5),
(95, 10, 6, '#8B008B', '#900090', 5),
(96, 10, 6, '#FF0000', '#FF0500', 5),
(97, 10, 6, '#00FF00', '#00F500', 5),
(98, 10, 6, '#0000FF', '#0500FF', 5),
(99, 10, 6, '#FFD700', '#FFDC00', 5),
(100, 10, 6, '#008080', '#008585', 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'roman', 'roman');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `level_pool`
--
ALTER TABLE `level_pool`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `leaderboard`
--
ALTER TABLE `leaderboard`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT untuk tabel `level_pool`
--
ALTER TABLE `level_pool`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
