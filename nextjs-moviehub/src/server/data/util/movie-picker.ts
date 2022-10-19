import type Genre from '@/server/data/enum/genre';
import Mood from '@/server/data/enum/mood';
import { getTopMoviesByGenre } from '@/server/data/client/tmdb-client';
import { type MovieDto } from '@/server/data/dto/movie-dto';

class MoodPicker {
	static async getMoviesForMood(
		moods: Array<keyof typeof Mood>,
		movieCount: number
	): Promise<MovieDto[]> {
		const movies = await this.fetchMoviesForMoods(moods);
		return this.getRandomMovies(movies, movieCount);
	}

	static async fetchMoviesForMoods(
		moods: Array<keyof typeof Mood>,
		needUniqueMovies?: boolean
	): Promise<MovieDto[]> {
		const movies: MovieDto[] = [];
		for (const mood of moods) {
			const genres: Genre[] = Mood[mood];
			for (const genre of genres) {
				const topMovies = await getTopMoviesByGenre(genre, 1, 10);
				movies.push(...topMovies);
			}
		}

		if (needUniqueMovies) {
			const unique = movies.filter(
				(value, index) => movies.indexOf(value) === index
			);

			return unique;
		}

		return movies;
	}

	private static getRandomMovies(
		movies: MovieDto[],
		movieCount: number
	): MovieDto[] {
		const randomMovies: MovieDto[] = [];
		for (let i = 0; i < movieCount; i++) {
			const randomIndex = Math.floor(Math.random() * movies.length);
			randomMovies.push(movies[randomIndex]);
			movies.splice(randomIndex, 1);
		}
		return randomMovies;
	}
}

export default MoodPicker;
