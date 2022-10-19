'use client';

import React from 'react';
import { signOut, useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';
import { toast } from 'react-toastify';

type PageProps = {
	params: {
		id?: string;
	};
};
// TODO add favourite and owned MovieLists.
const Page: React.FC<PageProps> = ({ params }) => {
	const { data, status } = useSession();
	const router = useRouter();

	if (status === 'unauthenticated' && data) {
		router.push('/login');
	} else {
		return (
			<div className="bg-white w-full h-full shadow-lg rounded-lg p-8 max-w-md mx-auto mt-10">
				<h1 className="text-3xl font-bold text-mh-theme mb-4 font-headings">
					User Profile
				</h1>
				<div className="mb-4">
					<p className="block text-mh-theme mb-2 font-bold font-headings">
						Username
					</p>
					<p className="w-full py-2 text-mh-theme font-bold font-content rounded-lg">
						{data?.user?.name}
					</p>
				</div>
				<div className="mb-4">
					<p className="block text-mh-theme font-bold font-headings mb-2">
						Email
					</p>
					<p className="w-full py-2 text-mh-theme font-bold font-content rounded-lg">
						{data?.user?.email}
					</p>
				</div>
				<div className="mb-4">
					<button
						onClick={() => {
							signOut({ redirect: true }).then(() => {
								toast.success('You have been signed out!');
								router.push('/login');
							});
						}}
						className="rounded-lg border text-white text-xl font-bold bg-mh-theme font-content p-3"
					>
						Sign out
					</button>
				</div>
			</div>
		);
	}
};

export default Page;
