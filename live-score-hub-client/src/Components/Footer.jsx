import { Typography, Box, Grid, Link } from '@mui/material';

export default function Footer() {
    return (
        <Box 
            component="footer"
            sx={{ 
                padding: '20px 0', 
                backgroundColor: '#04203F',
                color: 'white',
                textAlign: 'center',
                borderTop: '1px solid #ddd',
                marginTop: 'auto'
            }}
        >
            <Grid container justifyContent="center" spacing={3}>
                <Grid item>
                    <Link href="#" color="inherit" underline="none">About Us</Link>
                </Grid>
                <Grid item>
                    <Link href="#" color="inherit" underline="none">Contact</Link>
                </Grid>
                <Grid item>
                    <Link href="#" color="inherit" underline="none">Privacy Policy</Link>
                </Grid>
                <Grid item>
                    <Link href="#" color="inherit" underline="none">Terms of Service</Link>
                </Grid>
            </Grid>
            <Typography variant="body2" sx={{ marginTop: '20px' }}>
                &copy; 2024 Live Sports Hub. All rights reserved.
            </Typography>
        </Box>
    );
}
