/* eslint-disable prefer-arrow/prefer-arrow-functions */
import NextAuth, { getServerSession, type NextAuthOptions } from 'next-auth';
import GitHubProvider from 'next-auth/providers/github';
import DiscordProvider from 'next-auth/providers/discord';
import CredentialsProvider from 'next-auth/providers/credentials';

import { createUser, getUserByEmail } from '@/server/data/client/db-client';

export const getServerAuthSession = () => getServerSession(authOptions);

export const authOptions: NextAuthOptions = {
	pages: {
		signIn: '/login',
		signOut: '/login'
	},
	secret: process.env.NEXTAUTH_SECRET,
	providers: [
		GitHubProvider({
			name: 'github',
			clientId: process.env.GITHUB_ID as string,
			clientSecret: process.env.GITHUB_SECRET as string
		}),
		DiscordProvider({
			name: 'discord',
			clientId: process.env.DISCORD_CLIENT_ID as string,
			clientSecret: process.env.DISCORD_CLIENT_SECRET as string
		}),
		CredentialsProvider({
			name: 'credentials',
			credentials: {
				email: { label: 'email', type: 'text', placeholder: 'jsmith' },
				password: { label: 'password', type: 'password' }
			},
			async authorize(req) {
				if (!req) return null;

				const user = await getUserByEmail(req.email ?? ' ');

				if (user && user.Password === req.password) {
					const userObject = {
						id: user.UserId as string,
						name: user.Username as string,
						email: user.Email as string
					};

					return Promise.resolve(userObject);
				}

				return Promise.resolve(null);
			}
		})
	],
	callbacks: {
		async signIn(session) {
			let user = await getUserByEmail(session.user.email ?? ' ');
			console.log(user);
			if (!user) {
				const userData = {
					Username: session.user.name ?? ' ',
					Email: session.user.email ?? ' ',
					Password: '*',
					IsAdmin: 0
				};

				const result = await createUser(userData);

				if (result.success) {
					user = await getUserByEmail(session.user.email ?? ' ');
				} else {
					return false;
				}
			}

			return true;
		},
		async jwt(data) {
			const { token, account, user } = data;
			if (account) {
				token.user = user;
			}
			return token;
		},
		async session({ session, token }) {
			(session as unknown as { user?: typeof token.user }).user = token.user;

			return session;
		}
	}
};

export default NextAuth(authOptions);
