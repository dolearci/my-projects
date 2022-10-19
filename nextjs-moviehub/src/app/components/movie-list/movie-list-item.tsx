'use client';

import { useSession } from 'next-auth/react';
import Link from 'next/link';
import Image from 'next/image';

import { type ListType } from '@/types/movie-list';
import { useToggleFavoritMutation } from '@/hooks/movie-list';

import ListHeartButton from '../movie/list-heart-button';

import DeleteListButton from './delete-list-button';

type MovieListItemProps = {
	key: string;
	name: string;
	authorId: string;
	listId: number;
	listType: ListType;
	deletable: boolean;
};

const MovieListItem = ({
	key,
	name,
	authorId,
	listId,
	listType,
	deletable
}: MovieListItemProps) => {
	const session = useSession();
	const { mutate, isPending } = useToggleFavoritMutation();
	const userId = session?.data?.user?.id;
	const unfavStyle =
		'transition-colors duration-300 text-red-500 hover:text-gray-300';

	return (
		<li className="flex-row w-full shadow-md rounded-lg p-2 mb-2 text-mh-theme justify-center hover:shadow-lg transition-shadow duration-200 easy-in-out">
			<Link href={`/movie-lists/movie-list-detail/${listId}`} className="w-full flex justify-center items-center">
			<Image
				src="/list-icon.jpg"
				width={300}
				height={300}
				alt="movie list icon"
			/>
			</Link>
			<div className="flex w-full pb-3">
				<Link
					href={`/movie-lists/movie-list-detail/${listId}`}
					className="flex-row w-3/4 justify-between items-center pl-2"
					key={key}
				>
					<div className="flex-row justify-start ml-2 pt-1">
						<div className="flex justify-start font-headings text-xl">
							{name}
						</div>
						<div className="hidden sm:flex font-headings text-sm">
							Author:
							<span className="pl-2 ">
								{authorId === userId ? 'You' : authorId}
							</span>
						</div>
					</div>
				</Link>

				<div key={key} className="flex-row w-1/4 items-center justify-center">
					<div className="flex h-full justify-end items-center ml-5">
						{listType !== 'author' && authorId !== userId ? (
							isPending ? (
								<span className="loading loading-spinner loading-xl" />
							) : (
								<ListHeartButton
									className={listType === 'favourite' ? unfavStyle : undefined}
									onClick={() => {
										mutate({
											listId,
											userId,
											add: listType === 'favourite' ? false : true
										});
									}}
								/>
							)
						) : (
							deletable && <DeleteListButton listId={listId} userId={userId} />
						)}
					</div>
				</div>
			</div>
		</li>
	);
};

export default MovieListItem;
