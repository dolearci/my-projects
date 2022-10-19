import React, { useCallback, useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

import Button from '@/app/components/controls/button';
import Genre from '@/server/data/enum/genre';
import {
	getMoviesByGenreAndYear,
	getPopularMovies,
	getTopMoviesByGenre,
	getTopMoviesByYear,
	getTopRatedMovies
} from '@/server/data/client/tmdb-client';
import { type MovieDto } from '@/server/data/dto/movie-dto';

type Filter = {
	year: number | null;
	genre: Genre | '';
	page: number;
	perPage: number;
};

type MovieFiltersProps = {
	initialFilters: Filter;
	onMoviesFetched: (movies: MovieDto[]) => void;
};

const movieFilter = async (
	year: number | null,
	page: number,
	perPage: number,
	genre: Genre | '',
	isTopRatedClicked: boolean
) => {
	if (year && genre) {
		return await getMoviesByGenreAndYear(year, genre, page, perPage);
	} else if (year) {
		return await getTopMoviesByYear(year, page, perPage);
	} else if (genre) {
		return await getTopMoviesByGenre(genre, page, perPage);
	} else if (isTopRatedClicked) {
		return await getTopRatedMovies(page, perPage);
	} else {
		return await getPopularMovies(page, perPage);
	}
};

const MovieFilters: React.FC<MovieFiltersProps> = ({
	initialFilters,
	onMoviesFetched
}) => {
	const [filters, setFilters] = useState<Filter>({
		year: initialFilters.year,
		genre: initialFilters.genre,
		page: initialFilters.page,
		perPage: initialFilters.perPage ?? 12
	});

	const router = useRouter();

	const [isTopRatedClicked, setIsTopRatedClicked] = useState(false);

	const fetchMovies = useCallback(async () => {
		const { year, genre, page, perPage } = filters;

		const result: MovieDto[] = await movieFilter(
			year,
			page,
			perPage,
			genre,
			isTopRatedClicked
		);

		onMoviesFetched(result);
	}, [filters, isTopRatedClicked, onMoviesFetched]);

	useEffect(() => {
		setFilters(prevFilters => ({
			...prevFilters,
			page: initialFilters.page,
			perPage: initialFilters.perPage ?? 12
		}));
	}, [initialFilters.page, initialFilters.perPage]);

	useEffect(() => {
		fetchMovies();
	}, [fetchMovies, filters]);

	const handleYearChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
		setFilters(prevFilters => ({
			...prevFilters,
			year: parseInt(e.target.value) || null,
			page: 1
		}));

		router.push(`best-of/?page=1&per_page=${filters.perPage}`);
		setIsTopRatedClicked(false);
	};

	const handleGenreChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
		setFilters(prevFilters => ({
			...prevFilters,
			genre: e.target.value as unknown as Genre,
			page: 1
		}));
		router.push(`best-of/?page=1&per_page=${filters.perPage}`);
		setIsTopRatedClicked(false);
	};

	const handleTopRatedClick = () => {
		setFilters(prevFilters => ({
			...prevFilters,
			year: null,
			genre: '',
			page: 1
		}));
		router.push(`best-of/?page=1&per_page=${filters.perPage}`);
		setIsTopRatedClicked(true);
	};

	const handlePopularClick = () => {
		setFilters(prevFilters => ({
			...prevFilters,
			year: null,
			genre: '',
			page: 1
		}));

		router.push(`best-of/?page=1&per_page=${filters.perPage}`);
		setIsTopRatedClicked(false);
	};

	return (
		<div className="filters flex flex-wrap gap-4 p-4 rounded-lg bg-white shadow-lg mb-5">
			<div className="w-full text-mh-theme md:flex-1 lg:w-auto flex items-center">
				<label className="form-control w-full max-w-lg">
					<select
						id="year-select"
						className="select select-ghost w-full text-mh-theme font-headings shadow-md focus:bg-gray-200"
						value={filters.year ? filters.year.toString() : ''}
						onChange={handleYearChange}
					>
						<option className="label-text" disabled selected value="">
							Select Year
						</option>
						{[1980, 1990, 2000, 2010, 2020].map(year => (
							<option className="label-text" key={year} value={year}>
								{year}
							</option>
						))}
					</select>
				</label>
			</div>
			<div className="w-full text-mh-theme md:flex-1 lg:w-auto flex items-center ">
				<label className="form-control w-full max-w-lg">
					<select
						id="genre-select"
						className="select select-ghost w-full focus:bg-gray-200 shadow-md text-mh-theme font-headings"
						value={filters.genre}
						onChange={handleGenreChange}
					>
						<option disabled selected value="">
							Select Genre
						</option>
						{Object.values(Genre).map(
							value =>
								typeof value === 'number' && (
									<option key={value} value={value}>
										{Genre[value]}
									</option>
								)
						)}
					</select>
				</label>
			</div>
			<div className="w-full lg:flex-1 lg:w-1/4">
				<Button
					className=" w-full font-headings border-[1px] text-sm hover:bg-gray-200"
					onClick={handleTopRatedClick}
				>
					Show Top Rated
				</Button>
			</div>
			<div className="w-full lg:flex-1 lg:w-1/4">
				<Button
					className=" w-full font-headings border-[1px] text-sm hover:bg-gray-200"
					onClick={handlePopularClick}
				>
					Show Popular Movies
				</Button>
			</div>
		</div>
	);
};

export default MovieFilters;
