'use client';

import React, { useState } from 'react';

import MoodButton from '@/app/components/movie/mood-button';

type MovieList = {
	ListId: number;
	ListName: string;
	CreatorId: number;
	NumberOfMovies: number;
};

//TODO - Replace this with actual data from the database
const mockMovieLists: MovieList[] = [
	{ ListId: 1, ListName: 'Romantic Movies', CreatorId: 101, NumberOfMovies: 5 },
	{
		ListId: 2,
		ListName: 'Action Thrillers',
		CreatorId: 102,
		NumberOfMovies: 8
	},
	{
		ListId: 3,
		ListName: 'Sci-Fi Adventures',
		CreatorId: 103,
		NumberOfMovies: 4
	}
];

const Page: React.FC = () => {
	const [movieLists] = useState<MovieList[]>(mockMovieLists);

	const handleEdit = (movieList: MovieList) => {
		console.log('Edit:', movieList);
	};

	const handleDelete = (movieList: MovieList) => {
		console.log('Delete:', movieList);
	};

	return (
		<div className="min-h-screen text-white">
			<div className="container mx-auto py-8">
				<h1 className="text-4xl font-headings mb-6">Movie Lists Management</h1>
				<div className="overflow-x-auto">
					<table className="min-w-full">
						<thead>
							<tr>
								<th className="font-content px-6 py-3 text-left">List ID</th>
								<th className="font-content px-6 py-3 text-left">List Name</th>
								<th className="font-content px-6 py-3 text-left">Creator ID</th>
								<th className="font-content px-6 py-3 text-left">
									Number of Movies
								</th>
								<th className="font-content px-6 py-3 text-left">Actions</th>
							</tr>
						</thead>
						<tbody>
							{movieLists.map(movieList => (
								<tr key={movieList.ListId}>
									<td className="font-content px-6 py-4">{movieList.ListId}</td>
									<td className="font-content px-6 py-4">
										{movieList.ListName}
									</td>
									<td className="font-content px-6 py-4">
										{movieList.CreatorId}
									</td>
									<td className="font-content px-6 py-4">
										{movieList.NumberOfMovies}
									</td>
									<td className="font-content px-6 py-4 space-x-2">
										<MoodButton
											backgroundColor="blue"
											label="Edit"
											onClick={() => handleEdit(movieList)}
										/>
										<MoodButton
											backgroundColor="red"
											label="Delete"
											onClick={() => handleDelete(movieList)}
										/>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
			</div>
		</div>
	);
};

export default Page;
