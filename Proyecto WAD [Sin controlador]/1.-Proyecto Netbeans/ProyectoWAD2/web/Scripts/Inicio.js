const {
  Button, colors,createMuiTheme,CssBaseline,Dialog,DialogActions,DialogContent,DialogContentText,
  DialogTitle,Icon,MuiThemeProvider,Typography,Paper,withStyles,main,Avatar,FormControl,
  InputLabel,Input,FormControlLabel,form, Checkbox } = window['material-ui'];

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
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    
  },
  avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  },  
});
class Index extends React.Component {
  constructor(props){
      super(props);
      this.state = {
          email : "",
          password : ""
      }
  }
  handleSubmit = event => {
      event.preventDefault;
      const data = this.state;
      console.log(data);   
  }
  
  handleInputChange = (event) => {
       event.preventDefault;
       this.setState({
           [event.target.name] : event.target.value
       });
  }
  render() {
      
    const { email,password } = this.state;
    const { classes } = this.props;
    return (
      <main className={classes.main}>
        <CssBaseline />
      <Paper className={classes.paper}>
        <Avatar className={classes.avatar}>
            <Icon>lock</Icon>
        </Avatar>
        
        <Typography component="h1" variant="h5">
          Iniciar Sesión
        </Typography>
        <form action = "LoginAction" 
              method = "post" 
              className={classes.form} 
              onSubmit={this.handleSubmit}>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="email">Nombre</InputLabel>
            <Input id="email" 
                   name="email" 
                   autoComplete="email" 
                   autoFocus value = {this.state.email} 
                   onChange = {this.handleInputChange}/>
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="password"> Contraseña</InputLabel>
            <Input id="password" 
                   name="password" 
                   type="password" 
                   autoComplete="current-password" 
                   value = {this.state.password} 
                   onChange = {this.handleInputChange}/>
          </FormControl>       
          <Button type="submit"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className={classes.submit}
                  onClick={event => this.handleSubmit(event)}>
                  Entrar
          </Button>
        </form>
      </Paper>
      </main>
    );
  }
}
const Inicio = withStyles(styles)(Index);
ReactDOM.render(<Inicio />, document.getElementById('root'));