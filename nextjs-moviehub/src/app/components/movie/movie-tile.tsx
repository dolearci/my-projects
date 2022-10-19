import Image from 'next/image';
import React from 'react';

import MoodButton from '@/app/components/movie/mood-button';
import Genre from '@/server/data/enum/genre';

type MovieTileProps = {
	imageUrl: string;
	title: string;
	year: string;
	rating: string;
	description: string;
	genres: number[];
};

const MovieTile: React.FC<MovieTileProps> = ({
	imageUrl,
	title,
	year,
	rating,
	description,
	genres
}) => (
	<div className="animate-fadeIn bg-white transition duration-100 ease-in-out hover:shadow-md rounded-lg flex flex-col justify-between items-center text-black h-full">
		<Image
			width={200}
			height={300}
			src={`https://image.tmdb.org/t/p/w780${imageUrl}`}
			alt="Movie Poster"
			className="rounded-2xl mb-2 px-2 w-full hover:cursor-pointer"
		/>
		<div className="flex w-full px-4">
			<div
				className="w-4/5 flex flex-col justify-center"
				style={{ minHeight: '4.5em' }}
			>
				<h2 className="text-2xl font-semibold font-headings w-full text-left pt-1 line-clamp-2 overflow-hidden">
					{title}
				</h2>
				<div className="font-headings flex text-lg space-x-10 text-left pt-1">
					<p>{year}</p>
					<span className="text-xl">
						<span className="border-b-2 border-gray-300">{rating}</span>
						<span>/ 10</span>
					</span>
				</div>
			</div>
		</div>
		<div className="text-sm text-left w-full pt-4 mb-5 px-4 overflow-auto max-h-[150px] min-h-[100px]">
			{description}
		</div>
		<div className="flex items-center justify-center space-x-4 w-full mt-4 mb-4">
			{genres.slice(0, 2).map(genre => (
				<MoodButton
					key={genre}
					backgroundColor="white"
					label={Genre[genre]}
					className="cursor-default"
				/>
			))}
		</div>
	</div>
);

export default MovieTile;
