import type Genre from '@/server/data/enum/genre';

const API_KEY = '254c9e8b7606c8b1aa4c482ee7210268';

export const getMovieImages = async (id: number) => {
	try {
		const response = await fetch(
			`https://api.themoviedb.org/3/movie/${id}/images?api_key=${API_KEY}`
		);
		const data = await response.json();
		console.log(data);
	} catch (err) {
		console.error(err);
	}
};

export const searchMovie = async (query: string, page = 1, limit = 10) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/search/movie?api_key=${API_KEY}&query=${encodeURIComponent(query)}&language=en-US&page=${page}`
	);
	const data = await res.json();
	return data.results.slice(0, limit);
};

export const getPopularMovies = async (page = 1, limit = 10) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/movie/popular?api_key=${API_KEY}&language=en-US&page=${page}`
	);
	const data = await res.json();
	return data.results.slice(0, limit);
};

export const getMovieIdByName = async (movieName: string, page: number = 1) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/search/movie?api_key=${API_KEY}&query=${encodeURIComponent(movieName)}&language=en-US&page=${page}`
	);
	const data = await res.json();
	return data.results[0].id;
};

export const getMovieById = async (id: number) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}&language=en-US`
	);
	return await res.json();
};

export const getSimilarMoviesForId = async (id: number, limit: number = 1) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/movie/${id}/similar?api_key=${API_KEY}&language=en-US&page=1`
	);
	const data = await res.json();
	return data.results.slice(0, limit);
};
export const getMovieCast = async (id: number) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/movie/${id}/credits?api_key=${API_KEY}&language=en-US`
	);
	return await res.json();
};

export const getTopMoviesByYear = async (
	year: number,
	page: number = 1,
	limit: number = 10
) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&sort_by=popularity.desc&year=${year}&page=${page}`
	);
	const data = await res.json();
	return data.results.slice(0, limit);
};

export const getTopMoviesByGenre = async (
	genre: Genre,
	page: number = 1,
	limit: number = 10
) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&sort_by=popularity.desc&with_genres=${genre}&page=${page}`
	);
	const data = await res.json();
	return data.results.slice(0, limit);
};

export const getTopMoviesByActor = async (
	actorName: string,
	page: number = 1,
	limit: number = 10
) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/search/person?api_key=${API_KEY}&query=${encodeURIComponent(actorName)}&language=en-US&page=${page}`
	);
	const data = await res.json();
	const actorId = data.results[0].id;

	const topMoviesRes = await fetch(
		`https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&sort_by=popularity.desc&with_cast=${actorId}&page=${page}`
	);
	const topMoviesData = await topMoviesRes.json();
	return topMoviesData.results.slice(0, limit);
};

export const getTopRatedMovies = async (
	page: number = 1,
	limit: number = 10
) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/movie/top_rated?api_key=${API_KEY}&language=en-US&page=${page}`
	);
	const data = await res.json();
	return data.results.slice(0, limit);
};

export const getMoviesBySearch = async (
	query: string,
	page: number = 1,
	limit: number = 4
) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/search/movie?api_key=${API_KEY}&query=${encodeURIComponent(query)}&language=en-US&page=${page}`
	);
	const data = await res.json();
	return data.results.slice(0, limit);
};

export const getMoviesByGenreAndYear = async (
	year: number,
	genre: Genre,
	page: number = 1,
	limit: number = 10
) => {
	const res = await fetch(
		`https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&sort_by=popularity.desc&year=${year}&with_genres=${genre}&page=${page}`
	);

	const data = await res.json();
	return data.results.slice(0, limit);
};
