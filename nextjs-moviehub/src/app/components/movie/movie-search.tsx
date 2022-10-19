'use client';
import Image from 'next/image';
import React, { type ChangeEvent, useState } from 'react';
import Link from 'next/link';

import { getMoviesBySearch } from '@/server/data/client/tmdb-client';
import MovieTile from '@/app/components/movie/movie-tile';
import { type MovieDto } from '@/server/data/dto/movie-dto';

const searchMovies = async (
	searchTerm: string,
	setResult: (result: MovieDto[]) => void
) => {
	const result = await getMoviesBySearch(searchTerm);
	setResult(result);
};

const MovieSearch = () => {
	const [isOpen, setIsOpen] = useState(false);
	const [inputValue, setInputValue] = useState('');
	const [searchResults, setSearchResults] = useState<MovieDto[]>([]);

	const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
		setInputValue(event.target.value);
	};

	const handleSubmit = async () => {
		await searchMovies(inputValue, setSearchResults);
	};

	const handleOpenModal = () => {
		setIsOpen(true);
	};

	const handleCloseModal = () => {
		setIsOpen(false);
		setSearchResults([]);
	};

	return (
		<>
			<button onClick={handleOpenModal}>
				<Image
					src="/search.svg"
					alt="Search"
					width={50}
					height={50}
					className="flex"
				/>
			</button>
			{isOpen && (
				<div className="modal modal-open w-full h-full fixed inset-0 bg-black bg-opacity-50">
					<div className="modal-box bg-white relative p-10 sm:m-10 transition-all max-w-[300vh]">
						<button
							className="btn btn-sm text-black hover:text-white bg-white btn-circle absolute right-2 top-2"
							onClick={handleCloseModal}
						>
							✕
						</button>
						<div className="flex-row justify-center h-full items-center lg:flex w-full mt-2 mb-2">
							<input
								type="text"
								placeholder="Search for a movie..."
								className="input bg-white text-black input-bordered w-full lg:mr-2 lg:mb-0 mb-2"
								value={inputValue}
								onChange={handleInputChange}
								onKeyDown={event => {
									if (event.key === 'Enter') {
										handleSubmit();
									}
								}}
							/>
							<div className="modal-action w-full h-full flex justify-center items-center m-0 p-0">
								<button
									className="btn text-black ml-2 w-full hover:text-white bg-white"
									onClick={handleSubmit}
								>
									Submit
								</button>
							</div>
						</div>
						{searchResults.length > 0 && (
							<>
								<h1 className="text-mh-theme font-headings text-lg font-bold mt-6">
									Results
								</h1>
								<div className="search-results grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 w-full mt-5">
									{searchResults.map((movie, index) => (
										<Link key={index} href={`/movie-detail/${movie.id}`}>
											<MovieTile
												imageUrl={movie.poster_path}
												title={movie.title}
												year={movie.release_date.split('-')[0]}
												rating={movie.vote_average.toPrecision(2)}
												description={movie.overview}
												genres={movie.genre_ids}
											/>
										</Link>
									))}
								</div>
							</>
						)}
					</div>
				</div>
			)}
		</>
	);
};

export default MovieSearch;
