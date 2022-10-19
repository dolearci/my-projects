'use client';
import React, { useState } from 'react';
import Image from 'next/image';

import NavigationItem from './navigation-item';
import Link from 'next/link';
import MovieSearch from '../movie/movie-search';

type HamburgerMenuParameters = {
	loggedIn: boolean;
};
const HamburgerMenu = ({ loggedIn }: HamburgerMenuParameters) => {
	const [isOpen, setIsOpen] = useState(false);

	const toggleMenu = () => {
		setIsOpen(!isOpen);
	};

	return (
		<div>
			<button
				className={`py-3 px-4 rounded-lg hover:bg-gray-100 transition-colors duration-300 ease-in-out ${isOpen ? 'bg-gray-200' : ''}`}
				onClick={toggleMenu}
			>
				<Image src="/hamburger_menu.svg" alt="Search" width={40} height={42} />
			</button>
			<div>
				{isOpen && (
					<div className="animate-fadeInFast absolute top-0 left-0 mt-20 w-screen h-[55vh] border-b-4 border-mh-theme bg-white z-50 flex jusify-center">
						{loggedIn ? (
							<div className="flex justify-center w-screen text-lg">
								<div className="flex-col flex justify-between w-screen p-10">
									<div className="max-h-24 w-full flex justify-center ">
										<div className="border-2 rounded-md border-mh-theme w-[350px] hover:bg-gray-200 h-[60px] flex items-center justify-center  ">
											<Link
												className="p-4 "
												href="/movie-picker"
												onClick={toggleMenu}
											>
												Movie Picker
											</Link>
										</div>
									</div>
									<div className="max-h-24 w-full flex justify-center">
										<div className="border-2 rounded-md border-mh-theme w-[350px] hover:bg-gray-200 h-[60px] flex items-center justify-center  ">
											<Link
												className="p-4 "
												href="/best-of"
												onClick={toggleMenu}
											>
												Best Of
											</Link>
										</div>
									</div>
									<div className="max-h-24 w-full flex justify-center">
										<div className="border-2 rounded-md border-mh-theme w-[350px] hover:bg-gray-200 h-[60px] flex items-center justify-center  ">
											<Link
												className="p-4 "
												href="/movie-lists"
												onClick={toggleMenu}
											>
												Movie Lists
											</Link>
										</div>
									</div>
									<div className="max-h-24 w-full flex justify-center">
										<div className="border-2 rounded-md border-mh-theme w-[350px] hover:bg-gray-200 h-[60px] flex items-center justify-center  ">
											<Link
												className="p-4 "
												href="/movie-lists/my-lists"
												onClick={toggleMenu}
											>
												My Movie Lists
											</Link>
										</div>
									</div>
									<div className="max-h-24 w-full flex justify-center">
										<div className="border-2 rounded-md border-mh-theme w-[350px] hover:bg-gray-200 h-[100px] flex items-center justify-center  ">
											<MovieSearch />
										</div>
									</div>
								</div>
							</div>
						) : (
							<div className="flex justify-center w-screen">
								<div className="max-h-24 w-full flex justify-center">
									<div className="border-2 rounded-md border-mh-theme w-[350px] hover:bg-gray-200 h-[60px] flex items-center justify-center  ">
										<Link className="p-4 " href="/best-of" onClick={toggleMenu}>
											Best Of
										</Link>
									</div>
								</div>
								<div className="max-h-24 w-full flex justify-center">
									<div className="border-2 rounded-md border-mh-theme w-[350px] hover:bg-gray-200 h-[60px] flex items-center justify-center  ">
										<Link
											className="p-4 "
											href="/movie-lists"
											onClick={toggleMenu}
										>
											Movie Lists
										</Link>
									</div>
								</div>
							</div>
						)}
					</div>
				)}
			</div>
		</div>
	);
};

export default HamburgerMenu;
