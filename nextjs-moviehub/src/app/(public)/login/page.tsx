'use client';

import React, { useRef } from 'react';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import { z } from 'zod';
import { toast } from 'react-toastify';
import { getSession, signIn, useSession } from 'next-auth/react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useMutation } from '@tanstack/react-query';
import { type Session } from 'next-auth';

import { FormInput } from '@/app/components/users/forms/form-input';
import Button from '@/app/components/controls/button';
import Loading from '@/app/loading';

const schema = z.object({
	email: z.string().email(),
	password: z.string().min(6)
});

type FormSchema = z.infer<typeof schema>;

const authorize = async (user: FormSchema | null) => {
	if (!user) throw new Error('Invalid user data.');

	await signIn('credentials', {
		email: user.email,
		password: user.password,
		redirect: false
	});

	const session = await getSession();

	return session;
};

const Page = () => {
	const userData = useRef<FormSchema | null>(null);
	const router = useRouter();

	const loginMutation = useMutation<Session | null, Error>({
		mutationKey: ['authorization', userData],
		mutationFn: () => authorize(userData.current)
	});

	const form = useForm<FormSchema>({
		resolver: zodResolver(schema),
		defaultValues: {
			email: '',
			password: ''
		},
		mode: 'onChange'
	});

	const { data, status } = useSession();

	if (data) {
		router.push(`/profile/detail`);
	}

	if (status === 'loading') return <Loading />;
	const onSubmit = (user: FormSchema) => {
		userData.current = user;
		loginMutation.mutate(undefined, {
			onSuccess: session => {
				toast.success('Login successful!');

				if (!session?.user) {
					throw new Error('Session is null.');
				}
				window.location.reload();
				router.push(`/profile/detail`);
			},
			onError: () => {
				toast.error('Login failed.');
			}
		});
	};

	const handleOAuthLogin = async (
		e: React.MouseEvent<HTMLButtonElement>,
		chosenProvider: string
	) => {
		e.preventDefault();
		await signIn(chosenProvider);
	};

	return (
		<div className="flex w-full items-center justify-center my-20">
			<div className="w-[320px]">
				<FormProvider {...form}>
					<form onSubmit={form.handleSubmit(onSubmit)}>
						<FormInput
							placeholder="Email"
							name="email"
							label="Email"
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
							placeholder="Password"
							name="password"
							label="Password"
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
						<div className="flex w-full justify-center">
							<Button
								onClick={async e => {
									handleOAuthLogin(e, 'github');
								}}
								className="w-full"
							>
								Sign in with Github
							</Button>
						</div>
						<div className="flex w-full justify-center">
							<Button
								onClick={e => {
									handleOAuthLogin(e, 'discord');
								}}
								className="w-full text-lg  text-mh-theme rounded border-[2px] border-mh-theme p-3 my-1"
							>
								Sign in with Discord
							</Button>
						</div>
						<div className="flex w-full justify-center">
							<Link
								className="text-center p-2 text-mh-theme font-bold"
								href="/registration"
							>
								I dont have an account yet.
							</Link>
						</div>
						<Button
							type="submit"
							className="bg-mh-theme font-bold text-white text-xl w-full"
						>
							Login
						</Button>
					</form>
				</FormProvider>
			</div>
		</div>
	);
};

export default Page;
