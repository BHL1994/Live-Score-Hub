import React from 'react';
import { Container, Typography, Box, Grid, Paper, Link, Card, CardContent, CardMedia } from '@mui/material';
import headerBackground from "../images/header-background.jpg";
import featuredPost1 from "../images/featured-post1.jpg";
import featuredPost2 from "../images/featured-post2.jpeg";

export default function Home() {
    return (
        <Container maxWidth="lg">
            <main>
                <Paper 
                    elevation={1} 
                    style={{ 
                        padding: '40px 20px', 
                        marginBottom: '40px',
                        backgroundImage: `url(${headerBackground})`,
                        backgroundSize: 'cover', 
                        backgroundPosition: 'center', 
                        color: 'white',
                        position: 'relative',
                        borderRadius: '8px'
                    }}
                >
                    <Grid container>
                        <Grid item md={6}>
                            <Box style={{ 
                                padding: '20px', 
                                backgroundColor: 'rgba(0, 0, 0, 0.5)', 
                                borderRadius: '8px' 
                            }}>
                                <Typography variant="h3" gutterBottom>Welcome to Live Score Hub</Typography>
                                <Typography variant="h5" paragraph>
                                    Your one-stop site for keeping up with the scores of your favorite sports. Stay updated with live results, upcoming games, and much more, all in one place.
                                </Typography>
                            </Box>
                        </Grid>
                    </Grid>
                </Paper>
                
                <Grid container spacing={4}>
                    <Grid item xs={12} md={6}>
                        <Box component="a" href="/games/MLB" style={{ textDecoration: 'none' }}>
                            <Paper elevation={1} style={{ padding: '20px' }}>
                                <CardMedia
                                    component="img"
                                    height="140"
                                    image={featuredPost1}
                                    alt="MLB Featured Post"
                                />
                                <Typography variant="h5" style={{ marginTop: '10px' }}>MLB Season Heating Up</Typography>
                                <Typography variant="subtitle1">Catch all the live scores and updates as the MLB season heats up. Stay on top of your favorite teams and players.</Typography>
                                <Typography variant="subtitle1" style={{ color: 'blue' }}>Click here to keep up...</Typography>
                            </Paper>
                        </Box>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Box component="a" href="/games/NFL" style={{ textDecoration: 'none' }}>
                            <Paper elevation={1} style={{ padding: '20px' }}>
                                <CardMedia
                                    component="img"
                                    height="140"
                                    image={featuredPost2}
                                    alt="NFL Featured Post"
                                />
                                <Typography variant="h5" style={{ marginTop: '10px' }}>NFL Preseason is Underway</Typography>
                                <Typography variant="subtitle1">The NFL preseason is underway. Get ready for the action and keep up with all the latest scores and news.</Typography>
                                <Typography variant="subtitle1" style={{ color: "blue" }}>Click here to keep up...</Typography>
                            </Paper>
                        </Box>
                    </Grid>
                </Grid>
                
                <Grid container spacing={5} style={{ marginTop: '40px' }}>
                    <Grid item xs={12} md={8}>
                        <Typography variant="h6" gutterBottom>Latest News</Typography>
                        <hr />
                        
                        {/* First Blog Post */}
                        <Card elevation={1} style={{ marginBottom: '20px' }}>
                            <CardContent>
                                <Typography variant="h4" gutterBottom>Top Highlights from the Week</Typography>
                                <Typography variant="body1" paragraph><em>August 20, 2024 by Sports Analyst</em></Typography>
                                <Typography variant="body1" paragraph>
                                    This week in sports has been nothing short of exciting. From last-minute victories to record-breaking performances, we’ve got all the highlights that you might have missed. Stay tuned as we break down the key moments from each game.
                                </Typography>
                                <Typography variant="body1" paragraph>
                                    Whether it’s the MLB, NFL, NBA, or any other major league, our detailed analysis provides insight into what these results mean for your favorite teams and players. Don't miss out on any of the action!
                                </Typography>
                            </CardContent>
                        </Card>

                        {/* Second Blog Post */}
                        <Card elevation={1} style={{ marginBottom: '20px' }}>
                            <CardContent>
                                <Typography variant="h4" gutterBottom>The Rise of Emerging Talent</Typography>
                                <Typography variant="body1" paragraph><em>August 18, 2024 by Sports Enthusiast</em></Typography>
                                <Typography variant="body1" paragraph>
                                    With the new season underway, several young players have started making a name for themselves. We take a closer look at the rookies and emerging stars who are poised to become the next big names in sports.
                                </Typography>
                                <Typography variant="body1" paragraph>
                                    From record-setting debuts to game-changing performances, these players are not only making waves but also capturing the attention of fans and analysts alike. Find out who you should be watching this season.
                                </Typography>
                            </CardContent>
                        </Card>

                        {/* Third Blog Post */}
                        <Card elevation={1} style={{ marginBottom: '20px' }}>
                            <CardContent>
                                <Typography variant="h4" gutterBottom>Strategies for Fantasy Leagues</Typography>
                                <Typography variant="body1" paragraph><em>August 15, 2024 by Fantasy Guru</em></Typography>
                                <Typography variant="body1" paragraph>
                                    As the fantasy sports season kicks into gear, it’s time to refine your strategies and make the most out of your picks. Our expert tips will help you stay ahead of the competition.
                                </Typography>
                                <Typography variant="body1" paragraph>
                                    From sleeper picks to avoiding common pitfalls, we’ve got the advice you need to dominate your fantasy league. Learn how to maximize your points and make the right trades at the right time.
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                    <Grid item xs={12} md={4}>
                        <Paper elevation={0} style={{ padding: '20px' }}>
                            <Typography variant="h6" gutterBottom>About</Typography>
                            <Typography variant="body1">
                                Live Score Hub is your go-to platform for staying up-to-date with live sports scores, results, and schedules across major leagues. Whether you’re a fan of the MLB, NFL, NBA, or any other major sport, our mission is to provide you with real-time updates and comprehensive coverage, all in one place. We aim to bring sports enthusiasts closer to the action, ensuring you never miss a moment of the games that matter to you.
                            </Typography>
                        </Paper>
                    </Grid>
                </Grid>
            </main>
        </Container>
    );

}
