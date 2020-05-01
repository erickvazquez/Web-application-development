const {
  Button,
  colors,
  createMuiTheme,
  CssBaseline,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Icon,
  MuiThemeProvider,
  Typography,
  Paper,
  withStyles,
  main,
  Avatar,
  FormControl,
  InputLabel,
  Input,
  FormControlLabel,  
  Checkbox,
  Grid,
  form,
} = window['material-ui'];

const styles = theme => ({
    
  root: {
    textAlign: 'center',
    paddingTop: theme.spacing.unit * 20,
  },
  icon: {
    marginRight: theme.spacing.unit,
  },
  main: {
    width: 'auto',
    display: 'block', // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 490,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    align:'center',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    
  },
  bigAvatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
     width: 40,
    height: 40,
    marginBottom: 40
    
  },

  submit: {
    marginTop: theme.spacing.unit * 7,
    marginBottom: theme.spacing.unit * 6,
  }, 
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing.unit,
  }
});
class Indexx extends React.Component {

  render() {
   
    const { classes } = this.props;
    return (
      <main className={classes.main}>
        <CssBaseline />
       
      <Paper className={classes.paper}>
        <Avatar className={classes.bigAvatar} style={{backgroundColor: '#2196F3'}}>
            <Icon  fontSize="large" >cloud_upload</Icon>
        </Avatar>
        
        <Typography component="h1" variant="h5">
          Añade el archivo al servidor
        </Typography>
        <br/> <br/>
        <form action = "SubirArchivoAction" method = "post" encType = "multipart/form-data">
            <FormControl margin="normal" required fullWidth>
                <Input type = "file" name = "archivo" size = "45" />
            </FormControl>
            <Button
          
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Subir
          </Button>
        </form>
        
       
      </Paper>
      
      </main>
    );
  }
}
const SubirArchivo = withStyles(styles)(Indexx);
ReactDOM.render(<SubirArchivo />, document.getElementById('root'));