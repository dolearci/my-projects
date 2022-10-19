'use-client';
import Image from 'next/image';

type MoviePickerTileProps = {
	imageUrl: string;
	title: string;
};

const MoviePickerTile: React.FC<MoviePickerTileProps> = ({
	imageUrl,
	title
}) => (
	<div className="bg-white  rounded-lg h-[640px] w-[350px] flex flex-col justify-between items-center text-black 	">
		<div>
			<Image
				width={400}
				height={500}
				src={`https://image.tmdb.org/t/p/w780${imageUrl}`}
				alt="Movie Poster"
				className="rounded-[20px] mb-2 px-2 w-full"
			/>
			<div className="flex w-full px-4">
				<div className="w-full">
					<h2 className="text-2xl font-semibold font-headings w-full text-left pt-1">
						{title}
					</h2>
				</div>
			</div>
		</div>
	</div>
);

export default MoviePickerTile;
