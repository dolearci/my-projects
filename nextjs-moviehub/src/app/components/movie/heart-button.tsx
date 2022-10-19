'use client';
import React, { useRef, useState } from 'react';
import { FaHeart } from 'react-icons/fa';
import { type Row } from '@libsql/client';
import { useMutation } from '@tanstack/react-query';
import { toast } from 'react-toastify';

import {
	addMovieToList,
	createMovie,
	getMovie,
	type Movie
} from '@/server/data/client/db-client';

type HeartButtonProps = {
	movieLists?: Row[];
	movieId: number;
	movieTitle: string;
};

const createMovieHandler = async (movie: Movie) => {
	const result = await createMovie(movie);

	return result.success;
};

const addMovieToListHandler = async (movieId: number, listId: number) => {
	const result = await addMovieToList(movieId, listId);

	return result.success;
};

const checkMovieHandler = async (movieId: number) => {
	const result = await getMovie(movieId);

	return result === undefined;
};

const createMovieAndAddToList = async (
	movieId: number,
	listId: number,
	movie: Movie
) => {
	let movieResult;
	if (await checkMovieHandler(movieId)) {
		movieResult = await createMovieHandler(movie);
	} else {
		movieResult = true;
	}
	const listResult = await addMovieToListHandler(listId, movieId);

	return movieResult && listResult;
};

const HeartButton = ({ movieLists, movieId, movieTitle }: HeartButtonProps) => {
	const [isModalOpen, setModalOpen] = useState(false);

	const toggleModal = () => setModalOpen(!isModalOpen);

	const movieIdRef = useRef(0);
	const movieListId = useRef(0);

	const movie = {
		MovieId: movieId,
		Title: movieTitle
	};

	const mutation = useMutation({
		mutationKey: ['addMovieToList'],
		mutationFn: () =>
			createMovieAndAddToList(movieIdRef.current, movieListId.current, movie)
	});

	return (
		<>
			<button className="p-2" onClick={toggleModal}>
				<FaHeart
					size={45}
					className="transition-colors duration-300 hover:text-red-500 text-gray-300"
				/>
			</button>
			{isModalOpen && (
				<div className="modal modal-open items-center justify-center">
					<div className="modal-box bg-white p-6 w-full rounded-lg shadow-xl">
						<h3 className="font-bold font-headings text-xl mb-4">
							Your Movie Lists! <br /> Pick one to add the movie to!
						</h3>
						{movieLists?.map((row, index) => (
							<button
								key={index}
								className="btn block bg-white w-full text-left p-3 mb-2 text-black rounded hover:bg-gray-800 hover:text-white transition duration-300"
								onClick={() => {
									movieIdRef.current = movieId;
									movieListId.current = row.ListId as number;
									mutation.mutate(undefined, {
										onSuccess: () => {
											toast.success('Movie successfully added to the list!');
										},
										onError: () => {
											toast.error('Movie is already in the list!');
										}
									});
									toggleModal();
								}}
							>
								{String(row.ListName)}
							</button>
						))}
						<div className="modal-action">
							<button
								className="btn w-full p-3 bg-gray-200 text-black rounded hover:bg-gray-800 hover:text-white transition duration-300"
								onClick={toggleModal}
							>
								Close
							</button>
						</div>
					</div>
				</div>
			)}
		</>
	);
};

export default HeartButton;
