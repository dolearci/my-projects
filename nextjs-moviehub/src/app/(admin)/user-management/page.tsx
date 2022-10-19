'use client';

import React, { useState } from 'react';

import MoodButton from '@/app/components/movie/mood-button';

type User = {
	id: number;
	username: string;
	email: string;
	password: string;
	admin: number;
};

// TODO - Replace this with actual data from the database
const mockUsers: User[] = [
	{
		id: 1,
		username: 'john_doe',
		email: 'john@example.com',
		password: 'pass123',
		admin: 1
	},
	{
		id: 2,
		username: 'jane_smith',
		email: 'jane@example.com',
		password: 'pass123',
		admin: 0
	},
	{
		id: 3,
		username: 'sam_brown',
		email: 'sam@example.com',
		password: 'pass123',
		admin: 0
	}
];

const Page = () => {
	const [users] = useState<User[]>(mockUsers);

	const handleEdit = (user: User) => {
		console.log('Edit:', user);
	};

	const handleDelete = (user: User) => {
		console.log('Delete:', user);
	};

	return (
		<div className="min-h-screen text-white">
			<div className="container mx-auto py-8">
				<h1 className="text-4xl font-headings mb-6">User Management</h1>
				<div className="overflow-x-auto">
					<table className="min-w-full">
						<thead>
							<tr>
								<th className="font-content px-6 py-3 text-left">Username</th>
								<th className="font-content px-6 py-3 text-left">Email</th>
								<th className="font-content px-6 py-3 text-left">Password</th>
								<th className="font-content px-6 py-3 text-left">Admin</th>
								<th className="font-content px-6 py-3 text-left">Actions</th>
							</tr>
						</thead>
						<tbody>
							{users.map(user => (
								<tr key={user.id}>
									<td className="font-content px-6 py-4">{user.username}</td>
									<td className="font-content px-6 py-4">{user.email}</td>
									<td className="font-content px-6 py-4">{user.password}</td>
									<td className="font-content px-6 py-4">
										{user.admin ? 'Yes' : 'No'}
									</td>
									<td className="font-content px-6 space-x-2 py-4">
										<MoodButton
											backgroundColor="blue"
											label="Edit"
											onClick={() => handleEdit(user)}
										/>
										<MoodButton
											backgroundColor="red"
											label="Delete"
											onClick={() => handleDelete(user)}
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
