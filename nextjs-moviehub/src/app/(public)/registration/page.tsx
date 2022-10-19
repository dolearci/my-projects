'use client';

import React, { useRef } from 'react';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import { z } from 'zod';
import { toast } from 'react-toastify';
import { getSession, signIn, useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';
import { useMutation } from '@tanstack/react-query';
import { type Session } from 'next-auth';

import { FormInput } from '@/app/components/users/forms/form-input';
import { createUser, type User } from '@/server/data/client/db-client';
import Button from '@/app/components/controls/button';

const schema = z
	.object({
		email: z.string().email(),
		username: z.string().min(6),
		password: z
			.string()
			.min(6)
			.refine(password => /^(?=.*[A-Z])(?=.*[0-9])/.test(password), {
				message:
					'Password must contain at least one uppercase letter and one number'
			}),
		passwordConfirmation: z.string().min(6)
	})
	.refine(data => data.password === data.passwordConfirmation, {
		message: 'Passwords must match',
		path: ['passwordConfirmation']
	});

type FormSchema = z.infer<typeof schema>;

const createNewUser = async (user: FormSchema | null) => {
	if (!user) {
		throw new Error('Invalid registration data.');
	}

	const userModel: User = {
		Email: user.email,
		Username: user.username,
		Password: user.password
	};

	console.log('User model:', userModel);

	const result = await createUser(userModel);

	console.log('Result:', result);

	if (result.success) {
		await signIn('credentials', {
			email: user.email,
			password: user.password,
			redirect: false
		});
	} else {
		throw new Error('Failed to register.');
	}

	const session = await getSession();

	return session;
};

const Page = () => {
	const userData = useRef<FormSchema | null>(null);
	const router = useRouter();
	const { status } = useSession();

	const registrationMutation = useMutation<Session | null, Error>({
		mutationKey: ['registration', userData],
		mutationFn: () => createNewUser(userData.current)
	});

	const form = useForm<FormSchema>({
		resolver: zodResolver(schema),
		defaultValues: {
			email: '',
			username: '',
			password: '',
			passwordConfirmation: ''
		},
		mode: 'onChange'
	});

	if (status === 'loading') return <div>loading...</div>;

	const onSubmit = (user: FormSchema) => {
		userData.current = user;
		registrationMutation.mutate(undefined, {
			onSuccess: session => {
				toast.success('Registration successful!');

				if (!session?.user) {
					throw new Error('Session is null.');
				}

				router.push(`/profile/detail`);
			},
			onError: () => {
				toast.error('Registration failed.');
			}
		});
	};

	return (
		<div className="flex w-full items-center justify-center my-20">
			<div className="w-[320px]">
				<FormProvider {...form}>
					<form onSubmit={form.handleSubmit(onSubmit)}>
						<FormInput
							placeholder="Email"
							name="email"
							type="email"
							formSchema={schema}
							svgElement={
								<svg
									xmlns="http://www.w3.org/2000/svg"
									viewBox="0 0 16 16"
									fill="currentColor"
									className="w-4 h-4 opacity-70"
								>
									<path d="M2.5 3A1.5 1.5 0 0 0 1 4.5v.793c.026.009.051.02.076.032L7.674 8.51c.206.1.446.1.652 0l6.598-3.185A.755.755 0 0 1 15 5.293V4.5A1.5 1.5 0 0 0 13.5 3h-11Z" />
									<path d="M15 6.954 8.978 9.86a2.25 2.25 0 0 1-1.956 0L1 6.954V11.5A1.5 1.5 0 0 0 2.5 13h11a1.5 1.5 0 0 0 1.5-1.5V6.954Z" />
								</svg>
							}
						/>
						<FormInput
							placeholder="Username"
							name="username"
							type="text"
							formSchema={schema}
							svgElement={
								<svg
									xmlns="http://www.w3.org/2000/svg"
									viewBox="0 0 16 16"
									fill="currentColor"
									className="w-4 h-4 opacity-70"
								>
									<path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z" />
								</svg>
							}
						/>
						<FormInput
							placeholder="Password"
							name="password"
							type="password"
							formSchema={schema}
							svgElement={
								<svg
									xmlns="http://www.w3.org/2000/svg"
									viewBox="0 0 16 16"
									fill="currentColor"
									className="w-4 h-4 opacity-70"
								>
									<path
										fillRule="evenodd"
										d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
										clipRule="evenodd"
									/>
								</svg>
							}
						/>
						<FormInput
							placeholder="Confirm Password"
							name="passwordConfirmation"
							type="password"
							formSchema={schema}
							svgElement={
								<svg
									xmlns="http://www.w3.org/2000/svg"
									viewBox="0 0 16 16"
									fill="currentColor"
									className="w-4 h-4 opacity-70"
								>
									<path
										fillRule="evenodd"
										d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
										clipRule="evenodd"
									/>
								</svg>
							}
						/>

						<Button type="submit" className="w-full">
							Create account
						</Button>
					</form>
				</FormProvider>
			</div>
		</div>
	);
};

export default Page;
