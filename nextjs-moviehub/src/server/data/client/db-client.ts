import { createClient, type Row } from '@libsql/client';

export type Movie = {
	MovieId: number;
	Title: string;
};

export type User = {
	UserId?: number;
	Username: string;
	Email: string;
	Password: string;
	IsAdmin?: number;
};

export type MovieList = {
	ListId: number;
	ListName: string;
	CreatorId: string;
};

type FavoriteList = {
	FavoriteId: number;
	UserId: string;
	ListId: number;
};

const db = createClient({
	url: 'libsql://movie-hub-db-prod-yogiovic.turso.io',
	authToken:
		'eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJhIjoicnciLCJpYXQiOjE3MTU1MzM5MjQsImlkIjoiZDdkNDczMTEtZjQ2MC00ZWZlLTk3YmYtYTlhNWYwNzYxYWYxIn0.M12RLS3SV3ELxJZ-YXt6FudzaN1aBxYXTRUDsc89yLAHoTlOD1oyGU-FANjIMclkr6D55Ei5Au50kvr5_KndBA'
});

export const createUser = async (userData: User) => {
	const result = await db.execute({
		sql: 'INSERT INTO User (Username, Email, Password) VALUES (?, ?, ?)',
		args: [userData.Username, userData.Email ?? ' ', userData.Password]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const getUser = async (userId: string): Promise<Row> => {
	const result = await db.execute({
		sql: 'SELECT * FROM User WHERE UserId = ?',
		args: [userId]
	});

	return result.rows[0];
};

export const getUserByEmail = async (email: string) => {
	const resultSet = await db.execute({
		sql: 'SELECT * FROM User WHERE email = ?',
		args: [email]
	});

	return resultSet.rows[0];
};

export const updateUser = async (userId: string, userData: Partial<User>) => {
	const fields = Object.keys(userData)
		.map(field => `${field} = ?`)
		.join(', ');
	const values = Object.values(userData);

	const result = await db.execute({
		sql: `UPDATE User SET ${fields} WHERE UserId = ?`,
		args: [...values, userId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const deleteUser = async (userId: string) => {
	const result = await db.execute({
		sql: 'DELETE FROM User WHERE UserId = ?',
		args: [userId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const createMovie = async (movieData: Movie) => {
	const result = await db.execute({
		sql: 'INSERT INTO Movie (MovieId, Title) VALUES (?, ?)',
		args: [movieData.MovieId, movieData.Title]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const getMovie = async (movieId: number) => {
	const result = await db.execute({
		sql: 'SELECT * FROM Movie WHERE MovieId = ?',
		args: [movieId]
	});

	return result.rows[0];
};

export const updateMovie = async (
	movieId: number,
	movieData: Partial<Movie>
) => {
	const fields = Object.keys(movieData)
		.map(field => `${field} = ?`)
		.join(', ');
	const values = Object.values(movieData);

	const result = await db.execute({
		sql: `UPDATE Movie SET ${fields} WHERE MovieId = ?`,
		args: [...values, movieId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const deleteMovie = async (movieId: number) => {
	const result = await db.execute({
		sql: 'DELETE FROM Movie WHERE MovieId = ?',
		args: [movieId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const createMovieList = async (name: string, creatorId: string) => {
	const result = await db.execute({
		sql: 'INSERT INTO MovieList (ListName, CreatorId) VALUES (?, ?)',
		args: [name, creatorId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const getAllMovieLists = async (creatorId: number) => {
	const result = await db.execute({
		sql: 'SELECT * FROM MovieList WHERE CreatorId = ?',
		args: [creatorId]
	});

	return result.rows;
};

export const getMovieLists = async (): Promise<Row[]> => {
	const result = await db.execute('SELECT * FROM MovieList');

	return result.rows;
};

export const getMovieListsWithFavoritesByUser = async (
	userId: string
): Promise<Array<Row>> => {
	const result = await db.execute({
		sql: 'SELECT MovieList.*, FavoriteList.FavoriteId FROM MovieList LEFT OUTER JOIN FavoriteList ON FavoriteList.ListId = MovieList.ListId AND FavoriteList.UserId = ?',
		args: [userId]
	});

	return result.rows;
};

export const getMovieList = async (listId: number): Promise<Row> => {
	const result = await db.execute({
		sql: 'SELECT * FROM MovieList WHERE ListId = ?',
		args: [listId]
	});

	return result.rows[0];
};

export const getMovieListsByCreator = async (
	userId: string
): Promise<Row[]> => {
	const result = await db.execute({
		sql: 'SELECT * FROM MovieList WHERE CreatorId = ?',
		args: [userId]
	});

	return result.rows;
};

export const updateMovieList = async (
	listId: number,
	listData: Partial<MovieList>
) => {
	const fields = Object.keys(listData)
		.map(field => `${field} = ?`)
		.join(', ');
	const values = Object.values(listData);

	const result = await db.execute({
		sql: `UPDATE MovieList SET ${fields} WHERE ListId = ?`,
		args: [...values, listId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const deleteMovieList = async (listId: number) => {
	const result = await db.execute({
		sql: 'DELETE FROM MovieList WHERE ListId = ?',
		args: [listId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const addMovieToList = async (listId: number, movieId: number) => {
	const result = await db.execute({
		sql: 'INSERT INTO MovieListMovie (MovieListId, MovieId) VALUES (?, ?)',
		args: [listId, movieId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const removeMovieFromList = async (listId: number, movieId: number) => {
	const result = await db.execute({
		sql: 'DELETE FROM MovieListMovie WHERE MovieListId = ? AND MovieId = ?',
		args: [listId, movieId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const getMoviesInList = async (listId: number) => {
	const result = await db.execute({
		sql: 'SELECT Movie.* FROM MovieListMovie JOIN Movie ON MovieListMovie.MovieId = Movie.MovieId WHERE MovieListMovie.MovieListId = ?',
		args: [listId]
	});

	return result.rows;
};

export const createFavoriteList = async (listId: number, userId: string) => {
	const result = await db.execute({
		sql: 'INSERT INTO FavoriteList (UserId, ListId) VALUES (?, ?)',
		args: [userId, listId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const getFavoriteList = async (favoriteId: number): Promise<Row> => {
	const result = await db.execute({
		sql: 'SELECT * FROM FavoriteList WHERE FavoriteId = ?',
		args: [favoriteId]
	});

	return result.rows[0];
};

export const updateFavoriteList = async (
	favoriteId: number,
	favoriteData: Partial<FavoriteList>
) => {
	const fields = Object.keys(favoriteData)
		.map(field => `${field} = ?`)
		.join(', ');
	const values = Object.values(favoriteData);

	const result = await db.execute({
		sql: `UPDATE FavoriteList SET ${fields} WHERE FavoriteId = ?`,
		args: [...values, favoriteId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const deleteFavoriteList = async (favoriteId: number) => {
	const result = await db.execute({
		sql: 'DELETE FROM FavoriteList WHERE FavoriteId = ?',
		args: [favoriteId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const deleteFavoriteListByUserAndList = async (
	listId: number,
	userId: string
) => {
	const result = await db.execute({
		sql: 'DELETE FROM FavoriteList WHERE UserId = ? AND ListId = ?',
		args: [userId, listId]
	});

	return result.rowsAffected > 0 ? { success: true } : { success: false };
};

export const getFavoriteListsByUser = async (
	userId: string
): Promise<Array<Row>> => {
	const result = await db.execute({
		sql: 'SELECT FavoriteList.*, MovieList.* FROM FavoriteList JOIN MovieList ON FavoriteList.ListId = MovieList.ListId WHERE FavoriteList.UserId = ?',
		args: [userId]
	});

	return result.rows;
};
